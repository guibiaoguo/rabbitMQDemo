package com.ucsmy.rabbitmq;

import com.ucsmy.rabbitmq.impl.Producer;
import com.ucsmy.rabbitmq.impl.QueueConsumer;

import java.util.HashMap;

/**
 * Created by shentong on 2017/1/11.
 */
public class Application {

    public Application() {
        QueueConsumer consumer = null;
        try {
            consumer = new QueueConsumer("queue");
            Thread consumerThread = new Thread(consumer);
            consumerThread.start();
            Thread consumerThread1 = new Thread(consumer);
            consumerThread1.start();
            Producer producer = new Producer("queue");

            for (int i = 0; i < 100; i++) {
                HashMap message = new HashMap();
                message.put("message number", i);
                producer.sendMessage(message);
                System.out.println(Thread.currentThread().getName() + " Message Number "+ i +" sent.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        new Application();
    }
}
