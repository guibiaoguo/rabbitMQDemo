package com.ucsmy.rabbitmq.server;

import com.rabbitmq.client.ConnectionFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
/**
 * Created by shentong on 2017/1/11.
 */
public class Send {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello World1d234!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        connection.close();
    }
}