package com.hxiaol.demo.web;

import com.hxiaol.demo.bean.TestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @Autowired
    TestData testData;
    
    @GetMapping("/hello")
    public String helloWorld() {
        String reString= testData.getTest();
        try {
            Thread.currentThread().sleep(1);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return reString;
    }

}
