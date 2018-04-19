# lombok的使用说明

lombok是一个可以简写一些java bean的辅助方法。

通过引入注解达到自动生产某些代码的目的。

@ToString  //自动生成bean的toString方法。

@NoArgsConstructor //生成无参数构造函数

@AllArgsConstructor //生成所有参数的构造函数

@Setter //为字段生成，set方法

@Getter //为字段生成,get方法

@NonNull //标志字段不能为空

@RequiredArgsConstructor //利用nonNull字段生成构造函数

详情参考User类

@Cleanup //帮忙正确关闭文件流

参考App类

# 其他注意事项

eclipse的maven项目，如果引入lombok这个jar包，会编译报错 。 需要将lombok.jar这个maven下载后的jar包,放入 ${ECLIPSE_HOME}/dropins或者 ${ECLIPSE_HOME}/plugins或者前两者的路径中，然后重启eclispe。使得eclipse启动时就自动加载了lombok。

据说idea不会有这个问题。
