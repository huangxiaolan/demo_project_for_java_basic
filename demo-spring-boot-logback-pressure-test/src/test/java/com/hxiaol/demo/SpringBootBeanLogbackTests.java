package com.hxiaol.demo;

import com.hxiaol.demo.util.LogUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 让 JUnit 运行 Spring 的测试环境， 获得 Spring 环境的上下文的支持
@RunWith(SpringRunner.class)
// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest
public class SpringBootBeanLogbackTests {

	int threadNum = 10;
	ExecutorService executorService = Executors.newFixedThreadPool(threadNum);


    @Test
	public void test() {
		testLogManyTime(4000);
		testLogManyTime(40000);
		testLogManyTime(400000);
//		testLogManyTime(4000000);
//		testLogManyTime(40000000);
		testLogManyTime(400000000);
	}
    //@Test
	public void testAsync(){
        testLogAysncManyTime(4000);
        testLogAysncManyTime(40000);
        testLogAysncManyTime(400000);

        testLogAysncManyTime(400000000);

	}
    @Test
    public void testThrowable(){
        long start = System.currentTimeMillis();
        Throwable throwable = new Throwable();
        StackTraceElement[] stackTraceElements = throwable.getStackTrace();
        int l = stackTraceElements.length;
        long end = System.currentTimeMillis();


        System.out.println(end - start);
        System.out.println(l);
    }


	public void testLogManyTime(long count){
		long start = System.currentTimeMillis();
		for(long i=0;i<count;i++){
			LogUtil.logMessageTest();
		}
		long end = System.currentTimeMillis();
		LogUtil.logResult(String.format("log count %s cost %s ms simpleThread with calltrace without pattern-method",count,end-start));

	}
	public void testLogAysncManyTime(long count){

		//每个线程池需要
		long nCount = count / threadNum;


		long start = System.currentTimeMillis();

		CountDownLatch countDownLatch = new CountDownLatch(threadNum);

		for(int i=0;i<threadNum;i++){
			executorService.execute(()->{
				for(long j=0;j<nCount;j++){
					LogUtil.logMessageTest();
				}
				countDownLatch.countDown();

			});
		}
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {

		}
		long end = System.currentTimeMillis();
		LogUtil.logResult(String.format("log count %s cost %s ms mutiThread without call traceStageMemt",count,end-start));

	}


}
