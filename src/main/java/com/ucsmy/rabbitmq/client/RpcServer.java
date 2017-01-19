package com.ucsmy.rabbitmq.client;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Created by shentong on 2017/1/11.
 */
public class RpcServer {

    private final static String RPC_QUEUE_NAME = "rpc_queue2";

    public static void main(String[] argv)
            throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
//        factory.setUsername("remote_user");
//        factory.setPassword("123456");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        int prefetchCount = 1;
        channel.basicQos(prefetchCount); // accept only one unack-ed message at a time (see below);
        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");

                System.out.println(" [x] Received '" + message + "'");
                try {
//                    doWork(message);
                    long n = Long.parseLong(message);
                    System.out.println(" [.] fib(" + message + ")");
                    String response = "" + fib(n);
                    String callbackQueueName = channel.queueDeclare().getQueue();
                    AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                            .Builder()
                            .replyTo(callbackQueueName)
                            .correlationId(properties.getCorrelationId())
                            .build();
                    channel.basicPublish( "", properties.getReplyTo(),replyProps, response.getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    System.out.println(" [x] Done ");
                    //手动发送确认消息
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        //设置为false，关闭自动回复确认消息
        boolean autoAck = false; // acknowledgment is covered below
        channel.basicConsume(RPC_QUEUE_NAME, autoAck, consumer);
    }

    /**
     * 通过.进行睡眠设置
     * @param task
     * @throws InterruptedException
     */
    private static void doWork(String task) throws InterruptedException {
        for (char ch: task.toCharArray()) {
            if (ch == '.') Thread.sleep(100);
        }
    }

    private static long fib(long n) throws Exception {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fib(n-1) + fib(n-2);
    }
}
