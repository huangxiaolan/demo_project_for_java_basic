package com.hxiaol.demo.bean;

import org.springframework.stereotype.Component;

/**
 * 测试bean
 * @author hxl
 *
 */

@Component //测试这里的bean生成与@Bean注解生成的区别
public class User {

    String name;
    String phone;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    
}
