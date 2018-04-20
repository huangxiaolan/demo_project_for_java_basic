package com.hxiaol.test;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import com.hxiaol.demo.bean.User;

@Component
@EnableConfigurationProperties
public class UserWithEnableConfiguration {
    
   
    
    @ConfigurationProperties(prefix="user1")
    public User getUser() {
        
        return new User();
    }

}
