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
public class EmitLogDirectTest {

    private String message;
    private String logLevel;
    private EmitLogDirect emitLog;

    @Before
    public void setUp() throws Exception{
        emitLog = new EmitLogDirect("direct_logs");
    }

    @Parameterized.Parameters
    public static Collection<String[]> getTestParameters() {
        return Arrays.asList(new String[][]{
                {"info","first java message."},
                {"info","second java message.."},
                {"info","third java message..."},
                {"info","Fourth java message...."},
                {"info","Fifth java message....."},
                {"info","sixth java message......"},
                {"info","seventh java message......."},
                {"info","eighth java message........"},
                {"info","ninth java message........."},
                {"info","tenth java message.........."},
                {"warning","first java message."},
                {"warning","second java message.."},
                {"warning","third java message..."},
                {"warning","Fourth java message...."},
                {"warning","Fifth java message....."},
                {"warning","sixth java message......"},
                {"warning","seventh java message......."},
                {"warning","eighth java message........"},
                {"warning","ninth java message........."},
                {"warning","tenth java message.........."},
                {"error","first java message."},
                {"error","second java message.."},
                {"error","third java message..."},
                {"error","Fourth java message...."},
                {"error","Fifth java message....."},
                {"error","sixth java message......"},
                {"error","seventh java message......."},
                {"error","eighth java message........"},
                {"error","ninth java message........."},
                {"error","tenth java message.........."}
        });
    }

    public EmitLogDirectTest(String logLevel,String message)
    {
        this.logLevel = logLevel;
        this.message = message;
    }

    @Test
    public void testSend() throws Exception{
        emitLog.sendMessage(logLevel,message);
        emitLog.close();
    }

}
