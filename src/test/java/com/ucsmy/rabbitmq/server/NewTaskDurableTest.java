package com.ucsmy.rabbitmq.server;

import com.ucsmy.rabbitmq.server.NewTask;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by shentong on 2017/1/12.
 */

@RunWith(Parameterized.class)
public class NewTaskDurableTest {

    private String message;

    private NewTaskDurable newTaskDurable;

    @Before
    public void setUp() throws Exception {
        newTaskDurable = new NewTaskDurable("task_queue_ack_durable_qos2");
    }
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

    public NewTaskDurableTest(String message) {
        this.message = message;
    }

    @Test
    public void testSend() throws Exception{
        newTaskDurable.sendMessage(message);
        newTaskDurable.close();
    }

}
