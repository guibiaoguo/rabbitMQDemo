package com.ucsmy.rabbitmq.server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by shentong on 2017/1/13.
 */
@RunWith(Parameterized.class)
public class NewTaskTest {

    private String message;

    private NewTask newTask;
    @Before
    public void setUp() throws Exception {
        newTask = new NewTask("task_queue_ack2");
    }

    @Parameterized.Parameters
    public static Collection<String[]> getTestParameters() {
        List<String[]> msgs = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            msgs.add(new String[]{i+""});
        }
        return msgs;
//        return Arrays.asList(new String[][]{
//                {"first message."},
//                {"econd message.."},
//                {"third message..."},
//                {" Fourth message...."},
//                {" Fifth message....."},
//                {" sixth message......"},
//                {" seventh message......."},
//                {" eighth message........"},
//                {" ninth message........."},
//                {" tenth message.........."},
//        });
    }

    public NewTaskTest(String message) {
        this.message = message;
    }

    @Test
    public void testSend() throws Exception{
//        new NewTask("task_queue_ack2").sendMessage(message);
        //newTask.close();
        newTask.sendMessage(message);
        newTask.close();
    }

}
