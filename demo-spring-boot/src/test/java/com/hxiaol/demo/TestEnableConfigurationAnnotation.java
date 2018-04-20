package com.hxiaol.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.hxiaol.test.UserWithEnableConfiguration;
import com.hxiaol.test.UserWithoutEnableConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(value= {UserWithEnableConfiguration.class,UserWithoutEnableConfiguration.class})
public class TestEnableConfigurationAnnotation {

    @Autowired
    UserWithEnableConfiguration userWithEnableConfiguration;
    @Autowired
    UserWithoutEnableConfiguration userWithoutEnableConfiguration;
    @Test
    public void test() {
        //UserWithEnableConfiguration带有@EnableConfigurationProperties注解，所以配置生效。
        //@SpringBootApplication默认就会对路径下所有的类开启@EnableConfigurationProperties注解
        Assert.assertEquals("hello", userWithEnableConfiguration.getUser().getName());
        //无Enable注解所以配置不生效。
        Assert.assertEquals(null, userWithoutEnableConfiguration.getUser().getName());
    }

}
