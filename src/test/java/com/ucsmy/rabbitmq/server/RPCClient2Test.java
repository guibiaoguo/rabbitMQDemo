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
public class RPCClient2Test {

    private String message;

    private RPCClient2 rpcClient2;

    @Before
    public void setUp() throws Exception{
        rpcClient2 = new RPCClient2();
    }

    @Parameterized.Parameters
    public static Collection<String[]> getTestParameters() {
        return Arrays.asList(new String[][]{
                {"10"},
                {"11"},
                {"12"},
                {"13"},
                {"14"},
                {"15"},
                {"16"},
                {"17"},
                {"18"},
                {"19"},
        });
    }

    public RPCClient2Test(String message) {
        this.message = message;
    }

    @Test
    public void testSend() throws Exception{
        String response = rpcClient2.call(message);
        System.out.println(" [.] Got '" + response + "'");
        rpcClient2.close();
    }

}
