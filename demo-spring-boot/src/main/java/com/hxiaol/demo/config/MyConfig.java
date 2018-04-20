package com.hxiaol.demo.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Administrator
 *
 */
@Component      //不加这个注解的话, 使用@Autowired 就不能注入进去了
@ConfigurationProperties(prefix = "my")  // 配置文件中的前缀
public class MyConfig {
    private List<String> servers = new ArrayList<String>();
    public List<String> getServers() { return this.servers;
    }
}