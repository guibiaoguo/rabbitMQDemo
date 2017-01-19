package com.ucsmy.rabbitmq.server;

/**
 * Created by shentong on 2017/1/13.
 */

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class EmitLog {
    private static final String EXCHANGE_NAME = "logs2";
    protected Channel channel;
    protected Connection connection;

    private String exchange;

    public EmitLog (String exchange) throws Exception {
        this.exchange = exchange;
        ConnectionFactory factory = new ConnectionFactory();
        //设置远程登录地址
        factory.setHost("localhost");
//        factory.setUsername("remote_user");
//        factory.setPassword("123456");
        connection = factory.newConnection();
        channel = connection.createChannel();
//        boolean durable = false;
//        channel.queueDeclare(exchange, durable, false, false, null);
        boolean durable = true; //messages as durable
        //注册fanout广播模式的exchange
        channel.exchangeDeclare(exchange, "fanout",durable);
    }

    public void sendMessage(String message) throws Exception {
//        String message = getMessage(args);
        channel.basicPublish(exchange, "", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
    }

    public static void main(String[] argv) {

//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
//        Connection connection = factory.newConnection();
//        Channel channel = connection.createChannel();
//
//        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String message = "First Java Message!";//getMessage(argv);
        EmitLog emitLog = null;
        try {
            emitLog = new EmitLog("logs2");
            emitLog.sendMessage(message);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(emitLog != null)
                emitLog.close();
        }

//        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
//        System.out.println(" [x] Sent '" + message + "'");
//
//        channel.close();
//        connection.close();
    }

    private static String getMessage(String[] strings) {
        if (strings.length < 1)
            return "Hello World!";
        return joinStrings(strings, " ");
    }

    private static String joinStrings(String[] strings, String delimiter) {
        int length = strings.length;
        if (length == 0)
            return "";
        StringBuilder words = new StringBuilder(strings[0]);
        for (int i = 1; i < length;i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }

    public void close() {
        try {
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }
}
