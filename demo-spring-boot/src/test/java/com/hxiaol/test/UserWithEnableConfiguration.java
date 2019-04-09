package com.hxiaol.test;

import com.hxiaol.demo.bean.User;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserWithEnableConfiguration {
    
   
    
    @ConfigurationProperties(prefix="user1")
    public User getUser() {
        
        return new User();
    }

}
