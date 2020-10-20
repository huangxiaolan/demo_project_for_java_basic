package com.hxiaol;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class TestThreadNum {

    private static int threadPoolSize = 1;

    private static long taskNum = 100000;

    private static ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize, (a) -> {
        Thread thread = new Thread(a);
        thread.setName("test-executor");
        return thread;
    });

    public static void main(String[] args) {

        log.info("start test");
//        for(int poolSize = 1; poolSize<200;poolSize++){
//
//        }

        runTask(10,10000000,1);


//        for(int poolSize = 1; poolSize<200;poolSize++){
//            runTask(poolSize,20000,2);
//        }




    }

    public static void runTask(int threadPoolSize,long taskNum,int taskSleepMillSecond){
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize, (a) -> {
            Thread thread = new Thread(a);
            thread.setName("test-executor-"+threadPoolSize);
            return thread;
        });

        AtomicLong atomicLong = new AtomicLong(0);

        Runnable runnable = ()->{
            try {
                Thread.sleep(taskSleepMillSecond);
            } catch (InterruptedException e) {

            }
            atomicLong.incrementAndGet();
        };
        long start = System.currentTimeMillis();
        for(long i = 0;i<taskNum;i++){
            executorService.execute(runnable);
        }

        while(atomicLong.longValue() != taskNum){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {

            }
        }
        long end = System.currentTimeMillis();
        long total = end - start;
        System.out.println(String.format("threadPool:%s,taskNum:%s,taskSleep:%s,totalTime:%s,arvTime:%s",
                                         threadPoolSize,taskNum,taskSleepMillSecond,total,1.0*total / taskNum));
        executorService.shutdown();

    }
}