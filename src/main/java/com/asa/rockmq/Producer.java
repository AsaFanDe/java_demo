package com.asa.rockmq;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
/**
 * @Description
 * @Date 2019-08-21 16:24
 * @Author Asa
 * @Version 1.0
 **/
@Slf4j
public class Producer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new
                DefaultMQProducer("HSS_SOCKET_PRODUCT");
        // Specify name server addresses.
        producer.setNamesrvAddr("dev.mq.arounddoctor.com:9876");
        //Launch the instance.
        producer.start();

        Map map = new HashMap();
        map.put("zblx", "1");
        map.put("jcsj", "2019-08-20 15:28:00");
        Map pojo = new HashMap();
        pojo.put("ssy", "50");
        pojo.put("szy", "50");
        // pojo.put("xt", "666");
        map.put("pojo", pojo);
        Msg msg = new Msg("48010901", map);

        String json = JSONObject.toJSONString(msg);
        log.info(json);
        for (int i = 0; i < 1; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message message = new Message("HSS_SOCKET_MSG" /* Topic */,
                    "*" /* Tag */, "Q4HNU18123102581",
                    (json).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            SendResult sendResult = producer.send(message);
            System.out.printf("%s%n", sendResult);
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }

    @Data
    static class Msg implements Serializable{
        String type;
        Object body;

        public Msg(String type, Object body) {
            this.type = type;
            this.body = body;
        }
    }
}
