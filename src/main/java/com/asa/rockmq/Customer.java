package com.asa.rockmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @Description
 * @Date 2019-08-21 16:25
 * @Author Asa
 * @Version 1.0
 **/
public class Customer {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("HSS_SOCKET_CONSUMER");

        // Specify name server addresses.
        consumer.setNamesrvAddr("dev.mq.arounddoctor.com:9876");

        // consumer.setMessageModel(MessageModel.BROADCASTING); // 集群消息和广播消息

        // Subscribe one more more topics to consume.
        consumer.subscribe("HSS_SOCKET_MSG", "*");
        // Register callback to execute on arrival of messages fetched from brokers.
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                msgs.forEach(msg -> {
                    System.out.println(new String(msg.getBody()));
                });
                // System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        //Launch the consumer instance.
        consumer.start();

        System.out.printf("Consumer Started.%n");
    }
}
