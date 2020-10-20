package com.hxiaol.socketio.web;

import com.hxiaol.socketio.config.ConnectConfig;
import com.hxiaol.socketio.config.PressureConfig;
import com.hxiaol.socketio.manager.ManagerStarter;
import com.hxiaol.socketio.manager.SocketIoManager;
import com.hxiaol.socketio.metrics.Metric;
import com.hxiaol.socketio.unit.SocketConnectSingleUserUnitBuilder;
import com.hxiaol.socketio.utils.MessageConstants;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
public class WebController {

    @Autowired
    PressureConfig pressureConfig;

    @Autowired
    @Qualifier(value = MessageConstants.TEST_SCHEDULE_NAME)
    ScheduledExecutorService testScheduledExecutorService;

    @Autowired
    ScheduledExecutorService scheduledExecutorService ;

    @Autowired
    ConnectConfig connectConfig;


    @RequestMapping("start")
    public String start(int roomNum){
        Metric metric = new Metric();
        ConnectionPool connectionPool = new ConnectionPool(5, 5, TimeUnit.MINUTES);
        Dispatcher dispatcher = new Dispatcher();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(2, TimeUnit.SECONDS).build();
        pressureConfig.setRoomNum(roomNum);
        SocketConnectSingleUserUnitBuilder socketConnectSingleUserUnitBuilder = new SocketConnectSingleUserUnitBuilder(connectConfig,scheduledExecutorService,metric,connectionPool,dispatcher,okHttpClient);
        ManagerStarter managerStarter = new ManagerStarter(pressureConfig, testScheduledExecutorService, socketConnectSingleUserUnitBuilder);
        SocketIoManager socketIoManager = new SocketIoManager();
        managerStarter.startTest(socketIoManager);

        try {
            Thread.sleep(pressureConfig.getPressureSecond() * 1000);
        } catch (InterruptedException e) {

        }
        socketIoManager.stop();
        dispatcher.cancelAll();
        connectionPool.evictAll();
        String messge = metric.output();
        return messge;
    }
}