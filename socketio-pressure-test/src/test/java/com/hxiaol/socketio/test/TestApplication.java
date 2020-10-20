package com.hxiaol.socketio.test;

import com.hxiaol.socketio.config.ConnectConfig;
import com.hxiaol.socketio.config.PressureConfig;
import com.hxiaol.socketio.manager.ManagerStarter;
import com.hxiaol.socketio.manager.SocketIoManager;
import com.hxiaol.socketio.metrics.Metric;
import com.hxiaol.socketio.unit.SocketConnectSingleUserUnitBuilder;
import com.hxiaol.socketio.utils.MessageConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ScheduledExecutorService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplication {

    @Autowired
    ConnectConfig connectConfig;

    @Autowired
    SocketConnectSingleUserUnitBuilder socketConnectSingleUserUnitBuilder;

    @Autowired
    PressureConfig pressureConfig;

    @Autowired
    @Qualifier(value = MessageConstants.TEST_SCHEDULE_NAME)
    ScheduledExecutorService testScheduledExecutorService;

    @Autowired
    Metric  metric;

    @Test
    public void testConfig(){

        ManagerStarter managerStarter = new ManagerStarter(pressureConfig,testScheduledExecutorService,socketConnectSingleUserUnitBuilder);
        SocketIoManager socketIoManager = new SocketIoManager();
        managerStarter.startTest(socketIoManager);

        try {
            Thread.sleep(pressureConfig.getPressureSecond() * 1000);
        } catch (InterruptedException e) {

        }
        socketIoManager.stop();
        System.out.println(metric.output());

    }
}