package com.hxiaol.demo.kafka.controller;

import com.hxiaol.demo.kafka.produce.ProduceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerTest {
    @Autowired
    ProduceTest produceTest;

    @RequestMapping("/send")
    public String sendMq(String msg){
        produceTest.send("topic_toucai",msg);
        return "success";
    }
}