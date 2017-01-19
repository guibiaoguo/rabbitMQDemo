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
public class RpcClientTest {

    private String message;

    private RpcClient rpcClient;

    @Before
    public void setUp() throws Exception{
        rpcClient = new RpcClient("rpc_queue2");
    }

    @Parameterized.Parameters
    public static Collection<String[]> getTestParameters() {
        return Arrays.asList(new String[][]{
                {"1"},
                {"2"},
                {"3"},
                {"4"},
                {"5"},
                {"6"},
                {"7"},
                {"8"},
                {"9"},
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
                {"20"},
                {"21"},
                {"22"},
                {"23"},
                {"24"},
                {"25"},
                {"26"},
                {"27"},
                {"28"},
                {"29"},
                {"30"},
                {"31"},
                {"32"},
                {"33"},
                {"34"},
                {"35"},
                {"36"},
                {"37"},
                {"38"},
                {"39"},
                {"40"},
                {"41"},
                {"42"},
                {"43"},
                {"44"},
                {"45"},
                {"46"},
                {"47"},
                {"48"},
                {"49"}
        });
    }

    public RpcClientTest(String message) {
        this.message = message;
    }

    @Test
    public void testSend() throws Exception{
        String response = rpcClient.call(message);
        System.out.println(" [.] Got '" + response + "'");
        rpcClient.close();
    }

}
