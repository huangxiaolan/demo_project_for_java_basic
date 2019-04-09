package com.hxiaol.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.hxiaol.demo.bean.User;
import com.hxiaol.demo.testconfiguration.ConfigComponent;
import com.hxiaol.demo.testconfiguration.ConfigurationEntry;


// 让 JUnit 运行 Spring 的测试环境， 获得 Spring 环境的上下文的支持
@RunWith(SpringRunner.class)
// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest
public class SpringBootBeanAnnotationTests {
    @Autowired
    ConfigurationEntry configurationEntry;
    @Autowired
    ConfigComponent configComponent;

    @Autowired
    protected ApplicationContext ctx;
    @Autowired
    User user;
    

	@Test
	public void contextLoads() {
	    //configuration注解会注入bean
	    Assert.assertEquals(configurationEntry.getUser(), configurationEntry.getUser());
	   
	    //component不会注入
	    Assert.assertNotEquals(configComponent.getUser(), configComponent.getUser());
	    
	    
	    //user已经注入两次了。
	    Assert.assertEquals(2,ctx.getBeansOfType(User.class).size());
	    //userName从配置导入了
	    Assert.assertEquals("hello", configurationEntry.getUser().getName());
	    

	    
	}

}
