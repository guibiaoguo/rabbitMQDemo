package com.ucsmy.rabbitmq.server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by shentong on 2017/1/13.
 */
@RunWith(Parameterized.class)
public class NewTaskTest {

    private String message;

    @Parameterized.Parameters
    public static Collection<String[]> getTestParameters() {
        return Arrays.asList(new String[][]{
                {"first message."},
                {"econd message.."},
                {"third message..."},
                {" Fourth message...."},
                {" Fifth message....."},
                {" sixth message......"},
                {" seventh message......."},
                {" eighth message........"},
                {" ninth message........."},
                {" tenth message.........."},
        });
    }

    public NewTaskTest(String message) {
        this.message = message;
    }

    @Test
    public void testSend() throws Exception{
        new NewTask("task_queue_ack2").sendMessage(message);
        //newTask.close();
    }

}
