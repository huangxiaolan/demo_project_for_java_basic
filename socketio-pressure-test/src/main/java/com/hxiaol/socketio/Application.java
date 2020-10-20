package com.hxiaol.socketio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
//		String springConfig = "spring.profiles.active";
//		String config = System.getProperty(springConfig);
//		if(StringUtils.isEmpty(config)){
//			//使用默认配置
//			//TODO
//			System.setProperty(springConfig,"dev");
//		}
		SpringApplication.run(Application.class, args);

	}
}
