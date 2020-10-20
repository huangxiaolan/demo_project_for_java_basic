package com.hxiaol.socketio.manager;

import com.hxiaol.socketio.config.PressureConfig;
import com.hxiaol.socketio.unit.Unit;
import com.hxiaol.socketio.unit.UnitBuilder;
import lombok.AllArgsConstructor;

import java.util.concurrent.ScheduledExecutorService;

/**
 * 压测活动启动类
 */
@AllArgsConstructor
public class ManagerStarter {

    PressureConfig pressureConfig;

    ScheduledExecutorService scheduledExecutorService;

    UnitBuilder unitBuilder ;

    public Manager startTest(Manager manager ) {
        int roomNum = pressureConfig.getRoomNum();
        int roomNumStartPersecond = pressureConfig.getRoomNumStartPersecond();
        if (roomNum <= 0) {
            throw new IllegalArgumentException("roomNum not empty");
        }
        int sum = 0;
        while(sum <= roomNum){
            for (int i = 0; i < roomNumStartPersecond && sum <= roomNum; i++) {
                Unit unit = unitBuilder.build();
                manager.addUnit(unit);
                unit.start();
                sum ++ ;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        }

        return manager;
    }




}