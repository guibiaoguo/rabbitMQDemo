package com.ucsmy.rabbitmq.server;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by shentong on 2017/1/13.
 */
public class RpcClient {

    private final static String RPC_QUEUE_NAME = "rpc_queue2";
    protected Channel channel;
    protected Connection connection;
    protected String endPointName;
    private String queue;
    private String replyQueueName;

    public RpcClient(String queue) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        //设置远程登录地址
        factory.setHost("localhost");
//        factory.setUsername("remote_user");
//        factory.setPassword("123456");
        connection = factory.newConnection();
        channel = connection.createChannel();
        boolean durable = true;
        this.queue = queue;
        channel.queueDeclare(queue, false, false, false, null);
        int prefetchCount = 1;
        replyQueueName = channel.queueDeclare().getQueue();
        channel.basicQos(prefetchCount); // accept only one unack-ed message at a time (see below);
    }

    public String call(String message) throws Exception {
//        String message = getMessage(args);
        String uuid = UUID.randomUUID().toString();
        AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                .Builder()
                .correlationId(uuid)
                .replyTo(replyQueueName)
                .build();
        channel.basicPublish("", queue, replyProps, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
//                while (true) {
//                    if (properties.getCorrelationId().equals(uuid)) {
                        endPointName = new String(body);
//                        break;
//                    }
//                }
                System.out.println(" [x] Received  '" + envelope.getRoutingKey() + " : "+ endPointName + "'");
            }
        };

        boolean autoAck = true; // acknowledgment is covered below
        channel.basicConsume(replyQueueName, autoAck, consumer);
        //睡眠等待返回
        while(endPointName == null ) {
            Thread.sleep(1000);
        }
        return endPointName;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(" [x] Requesting fib(11)");
        RpcClient rpcClient = new RpcClient(RPC_QUEUE_NAME);
        String response = rpcClient.call("11");
        System.out.println(" [.] Got '" + response + "'");
        rpcClient.close();
    }

    public void close() throws Exception {
        channel.close();
        connection.close();
    }
}
