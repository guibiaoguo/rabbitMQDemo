package com.ucsmy.rabbitmq.client;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Created by shentong on 2017/1/11.
 */
public class WorkerDurable {

    private final static String TASK_QUEUE_NAME = "task_queue_ack_durable_qos2";

    public static void main(String[] argv)
            throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
//        factory.setHost("172.17.22.187");
//        factory.setUsername("remote_user");
//        factory.setPassword("123456");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        boolean durable = true; //messages as durable
        channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        int prefetchCount = 1;
        channel.basicQos(prefetchCount); // accept only one unack-ed message at a time (see below);
        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");

                System.out.println(" [x] Received '" + message + "'");
                try {
                    doWork(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    System.out.println(" [x] Done");
                    //手动发送确认消息
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        //设置为false，关闭自动回复确认消息
        boolean autoAck = false; // acknowledgment is covered below
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, consumer);
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
