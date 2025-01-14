package org.apache.eventmesh.api.consumer;

import java.util.List;
import java.util.Properties;

import io.openmessaging.api.AsyncMessageListener;
import io.openmessaging.api.Consumer;
import io.openmessaging.api.Message;

import org.apache.eventmesh.api.AbstractContext;

public interface MeshMQPushConsumer extends Consumer {

    void init(Properties keyValue) throws Exception;

    @Override
    void start();

//    void updateOffset(List<MessageExt> msgs, ConsumeConcurrentlyContext context);

    void updateOffset(List<Message> msgs, AbstractContext context);

//    void registerMessageListener(MessageListenerConcurrently messageListenerConcurrently);

    void subscribe(String topic, final AsyncMessageListener listener) throws Exception;

    @Override
    void unsubscribe(String topic);

    AbstractContext getContext();
}
