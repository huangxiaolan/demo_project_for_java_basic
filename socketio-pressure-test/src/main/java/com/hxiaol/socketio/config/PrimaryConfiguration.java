package com.hxiaol.socketio.config;

import com.hxiaol.socketio.metrics.Metric;
import com.hxiaol.socketio.unit.SocketConnectSingleUserUnitBuilder;
import com.hxiaol.socketio.utils.MessageConstants;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class PrimaryConfiguration {

    @Bean
    public ConnectConfig getConnnectConfig() {
        return new ConnectConfig();
    }

    @Bean
    public PressureConfig getPressureConfig() {
        return new PressureConfig();
    }

    @Primary
    @Bean
    public ScheduledExecutorService getScheduleService() {
        //TODO 考虑进程监控
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors() * 2);
        return scheduledExecutorService;
    }
    @Bean
    public Dispatcher getDispatcher(){
        return new Dispatcher();
    }

    @Bean(MessageConstants.TEST_SCHEDULE_NAME)
    public ScheduledExecutorService getTestScheduleService() {
        //TODO 考虑进程监控
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors() * 2);
        return scheduledExecutorService;
    }

    @Bean
    public OkHttpClient getOkHttpClient(){
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(15000);
        dispatcher.setMaxRequestsPerHost(15000);
        ConnectionPool connectionPool = new ConnectionPool(2000,5L,TimeUnit.MILLISECONDS);
        return new OkHttpClient.Builder().dispatcher(dispatcher).connectionPool(connectionPool).connectTimeout(2, TimeUnit.SECONDS).build();
    }

    @Bean
    public ConnectionPool getConnectionPool() {
        return new ConnectionPool(50, 5, TimeUnit.MINUTES);
    }

    @Bean
    public Metric getMetric() {
        return new Metric();
    }

    @Bean
    public SocketConnectSingleUserUnitBuilder getSocketConnectSingleUserUnitBuilder(ConnectConfig connectConfig,
            ScheduledExecutorService scheduledExecutorService, Metric metric, ConnectionPool connectionPool,Dispatcher dispatcher,OkHttpClient okHttpClient) {
        return new SocketConnectSingleUserUnitBuilder(connectConfig, scheduledExecutorService, metric, connectionPool,dispatcher,okHttpClient);
    }

}