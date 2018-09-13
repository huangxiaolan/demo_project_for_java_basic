package com.hxiaol.demo.kafka.produce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProduceTest {

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    public void send(String topic,String msg){
        kafkaTemplate.send(topic,msg);
        return;
    }
}