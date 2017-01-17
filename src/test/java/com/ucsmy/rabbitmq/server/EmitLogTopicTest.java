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
public class EmitLogTopicTest {

    private String message;
    private String logLevel;
    private EmitLogTopic emitLog;

    @Before
    public void setUp() throws Exception{
        emitLog = new EmitLogTopic("topic_logs");
    }

    @Parameterized.Parameters
    public static Collection<String[]> getTestParameters() {
        return Arrays.asList(new String[][]{
                {"kern.critical","first java message."},
                {"kern.critical","second java message.."},
                {"kern.critical","third java message..."},
                {"kern.critical","Fourth java message...."},
                {"kern.critical","Fifth java message....."},
                {"kern.critical","sixth java message......"},
                {"kern.critical","seventh java message......."},
                {"kern.critical","eighth java message........"},
                {"kern.critical","ninth java message........."},
                {"kern.critical","tenth java message.........."},
                {"kern.critis","first java message."},
                {"kern.critis","second java message.."},
                {"kern.critis","third java message..."},
                {"kern.critis","Fourth java message...."},
                {"kern.critis","Fifth java message....."},
                {"kern.critis","sixth java message......"},
                {"kern.critis","seventh java message......."},
                {"kern.critis","eighth java message........"},
                {"kern.critis","ninth java message........."},
                {"kern.critis","tenth java message.........."},
                {"kerns.critical","first java message."},
                {"kerns.critical","second java message.."},
                {"kerns.critical","third java message..."},
                {"kerns.critical","Fourth java message...."},
                {"kerns.critical","Fifth java message....."},
                {"kerns.critical","sixth java message......"},
                {"kerns.critical","seventh java message......."},
                {"kerns.critical","eighth java message........"},
                {"kerns.critical","ninth java message........."},
                {"kerns.critical","tenth java message.........."},
                {"kerns.criticals","first java message."},
                {"kerns.criticals","second java message.."},
                {"kerns.criticals","third java message..."},
                {"kerns.criticals","Fourth java message...."},
                {"kerns.criticals","Fifth java message....."},
                {"kerns.criticals","sixth java message......"},
                {"kerns.criticals","seventh java message......."},
                {"kerns.criticals","eighth java message........"},
                {"kerns.criticals","ninth java message........."},
                {"kerns.criticals","tenth java message.........."}
        });
    }

    public EmitLogTopicTest(String logLevel, String message)
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
