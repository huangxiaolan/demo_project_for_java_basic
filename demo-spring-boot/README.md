# spring boot 介绍

spring boot是一个工具，可以快速上手spring相关项目的工具,简化spring应用的开发。建议使用jdk8.

参考文档:https://github.com/qibaoguang/Spring-Boot-Reference-Guide

下面介绍一些重要的功能

## @SpringBootApplication
@SpringBootApplication 等价于以下三者:

@EnableAutoConfiguration : 自动引入starter相关的依赖

@ComponentScan : 自动注入当前包下面所有的子类

@Configuration : 表明这是一个增强的@Component类，里面@Bean注解的内容会自动注入spring中。


## @ConfigurationProperties

@ConfigurationProperties 可以将application.yml的数据注入到对象中。

yml的数据结构。
```
my:
  servers:
    - dev.bar.com
    - foo.bar.com
    - jiaobuchong.com
```

要求开启@EnableConfigurationProperties支持，@SpringBootApplication对路径下的类默认开启该支持。

细节可以参考测试类TestEnableConfigurationAnnotation

## @EnableConfigurationProperties

注入ConfigurationProperties 注解的类

## spring默认logback的配置。

启动spring使用 --debug --trace 可以得到spring启动非常详细的日志。


logging:
  config: classpath:logback-spring-test_yn.xml
  # 默认加载logback-spring.xml 文件

配置属性不允许出现大写，大写用-进行分割

# spring boot 自定义starter

参考：https://www.jianshu.com/p/45538b44e04e

starter是一个maven工程，里面定义的组件的jar依赖，同时在resources/META-INF/spring.factories定义了以下格式的文件
```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.example.autocinfigure.ExampleAutoConfigure
```
定义了后面的类，spring boot启动时会去尝试加载,加载后该类以及该类依赖的类都会被spring管理到，现有程序就能够使用组件的类了。

>总结下Starter的工作原理
>
>Spring Boot在启动时扫描项目所依赖的JAR包，寻找包含spring.factories文件的JAR包
>根据spring.factories配置加载AutoConfigure类
>根据 @Conditional注解的条件，进行自动配置并将Bean注入Spring Context

其实善用注解的@Import注解，也能够很好的达到引入组件的目的。 公司的项目就是这么干的，就不展开了。

## springmvc注解

引入
```
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
即可使用spring web相关的注解，如@RestController，@RequestMapping


## spring boot 多环境配置与启动命令

默认加载顺序位置:
–file:./config/

–file:./

–classpath:/config/

–classpath:/

指定配置的位置  --spring.config.location=G:/application.properties

启动时指定加载配置名称 --spring.profiles.active=dev

首先加载  application-{profile}.yml 文件， 没有再去加载application.yml 数据

打包命令：

mvn package spring-boot:repackage

启动服务命令,根据不同环境配置不同的变量。

java -jar target/demo-spring-boot-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev