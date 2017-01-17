package com.ucsmy.rabbitmq.impl;

import com.ucsmy.rabbitmq.EndPoint;
import org.apache.commons.lang.SerializationUtils;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by shentong on 2017/1/11.
 */
public class Producer extends EndPoint{

    public Producer(String endpointName) throws Exception {
        super(endpointName);
    }

    public void sendMessage(Serializable object) throws IOException {
        channel.basicPublish("",endPointName, null, SerializationUtils.serialize(object));
    }
}
