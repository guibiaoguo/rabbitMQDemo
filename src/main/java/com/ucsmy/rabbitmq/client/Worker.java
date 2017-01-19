package com.ucsmy.rabbitmq.client;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Created by shentong on 2017/1/11.
 */
public class Worker {
    private final static String TASK_QUEUE_NAME = "task_queue_ack2";

    public static void main(String[] argv)
            throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        //设置远程登录地址
//        factory.setHost("172.17.22.187");
//        factory.setUsername("remote_user");
//        factory.setPassword("123456");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
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
                }
            }

            private void doWork(String task) throws InterruptedException {
                for (char ch: task.toCharArray()) {
                    if (ch == '.') Thread.sleep(100);
                }
            }
        };
        boolean autoAck = true; // acknowledgment is covered below
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, consumer);
    }
}
