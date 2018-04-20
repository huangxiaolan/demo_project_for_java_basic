package com.hxiaol.test;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.hxiaol.demo.bean.User;

@Component

public class UserWithoutEnableConfiguration {
    
    @ConfigurationProperties(prefix="user1")
    public User getUser() {
        return new User();
    }

}
