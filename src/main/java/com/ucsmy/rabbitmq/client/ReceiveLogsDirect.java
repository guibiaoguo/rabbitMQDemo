package com.ucsmy.rabbitmq.client;

/**
 * Created by shentong on 2017/1/13.
 */
import com.rabbitmq.client.*;

import java.io.IOException;

public class ReceiveLogsDirect {

    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        //声明一个空的queue
        String queueName = channel.queueDeclare().getQueue();
        String[] logLevels = new String[]{"warning","info","error"};
        //绑定fanout类型的exchange和没有名字的queue
        for(String severity : logLevels){
            channel.queueBind(queueName, EXCHANGE_NAME, severity);
        }

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + envelope.getRoutingKey() + " :"+ message + "'");
                try {
                    doWork(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    System.out.println(" [x] Done  ");
                    //手动发送确认消息
//                   channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        //设置为false，关闭自动回复确认消息
        boolean autoAck = true; // acknowledgment is covered below
        channel.basicConsume(queueName, autoAck, consumer);
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
}
