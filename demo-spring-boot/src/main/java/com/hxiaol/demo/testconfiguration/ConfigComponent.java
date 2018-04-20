package com.hxiaol.demo.testconfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.hxiaol.demo.bean.User;

@Component
public class ConfigComponent {
   

    @Bean
    public User getUser() {
        return new User();
    }
    
    
    
    public User getUserFromOtherMethon() {
        return getUser();
    }
}
