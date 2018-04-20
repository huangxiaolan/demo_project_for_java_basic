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



@RunWith(SpringRunner.class)
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
