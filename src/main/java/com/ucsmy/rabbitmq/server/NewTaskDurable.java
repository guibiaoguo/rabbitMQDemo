package com.ucsmy.rabbitmq.server;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * Created by shentong on 2017/1/13.
 */
public class NewTaskDurable {

    private final static String TASK_QUEUE_NAME = "task_queue_ack_durable";
    protected Channel channel;
    protected Connection connection;
    protected String endPointName;
    private String queue;

    public NewTaskDurable(String queue) throws Exception {
        this.queue = queue;
        ConnectionFactory factory = new ConnectionFactory();
        //设置远程登录地址
        factory.setHost("172.17.22.187");
        factory.setUsername("remote_user");
        factory.setPassword("123456");
        connection = factory.newConnection();
        channel = connection.createChannel();
        boolean durable = true;
        channel.queueDeclare(queue, durable, false, false, null);
    }

    public void sendMessage(String message) throws Exception {
//        String message = getMessage(args);
        channel.basicPublish("", queue, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
    }

    public static void main(String[] args) throws Exception {
        new NewTask("task_queue_ack_durable_qos").sendMessage("First message.");
    }

    private static String getMessage(String[] strings) {
        if (strings.length < 1)
            return "Hello World!";
        return joinStrings(strings, " ");
    }

    private static String joinStrings(String[] strings, String delimiter) {
        int length = strings.length;
        if (length == 0) return "";
        StringBuilder words = new StringBuilder(strings[0]);
        for (int i = 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }

    public void close() throws Exception {
        channel.close();
        connection.close();
    }
}
