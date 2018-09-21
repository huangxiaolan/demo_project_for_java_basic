package com.hxiaol.demo.retry;

import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.base.Predicates;
import com.hxiaol.demo.retry.listener.TestRetryListener;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class App {
    private static Callable<Boolean> testWithFalseCall = new Callable<Boolean>() {

        public Boolean call() throws Exception {

            return false;
        }
    };

    private static Callable<Boolean> testWithRunTimeExceptionCall = new Callable<Boolean>() {

        public Boolean call() throws Exception {

            throw new RuntimeException();
        }
    };
    public static void main(String[] args) {
        Retryer<Boolean> retryer = RetryerBuilder
                .<Boolean>newBuilder()
                //抛出runtime异常、checked异常时都会重试，但是抛出error不会重试。

                //返回false也需要重试
                .retryIfResult(Predicates.equalTo(false))
                //重调策略
                .withWaitStrategy(WaitStrategies.fixedWait(100, TimeUnit.MILLISECONDS))
                //尝试次数
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))

                .withRetryListener(new TestRetryListener<Boolean>())
                .build();

        try {
            //当前线程中执行多次重试
            retryer.call(testWithRunTimeExceptionCall);
        } catch (ExecutionException e) {
            //执行时间超过预设时间后抛出该异常
            e.printStackTrace();

        } catch (RetryException e) {
            //出现未知异常导致的重试终止或者重试次数耗尽抛出该异常
            e.printStackTrace();
        }
    }
}