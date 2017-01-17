package com.ucsmy.rabbitmq.server;

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
public class EmitLogTest {

    private String message;

    private EmitLog emitLog;

    @Before
    public void setUp() throws Exception{
        emitLog = new EmitLog("logs2");
    }

    @Parameterized.Parameters
    public static Collection<String[]> getTestParameters() {
        return Arrays.asList(new String[][]{
                {"first java message."},
                {"second java message.."},
                {"third java message..."},
                {"Fourth java message...."},
                {"Fifth java message....."},
                {"sixth java message......"},
                {"seventh java message......."},
                {"eighth java message........"},
                {"ninth java message........."},
                {"tenth java message.........."},
        });
    }

    public EmitLogTest(String message) {
        this.message = message;
    }

    @Test
    public void testSend() throws Exception{
        emitLog.sendMessage(message);
        emitLog.close();
    }

}
