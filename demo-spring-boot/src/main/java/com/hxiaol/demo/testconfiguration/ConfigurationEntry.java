package com.hxiaol.demo.testconfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hxiaol.demo.bean.User;

@Configuration
public class ConfigurationEntry {
    
    @Autowired
    ConfigComponent config;

    @Bean
    @ConfigurationProperties(prefix="user1")
    public User getUser() {
        
        User user=new User();
        
        return user;
    }
    
    
    
    
}
