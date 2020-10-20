package com.hxiaol.socketio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *  压测配置
 */
@Data
@ConfigurationProperties(prefix = "data.pressure")
public class PressureConfig {

    int roomNum = 1 ; // 启动房间数量

    int pressureSecond = 300 ; // 压测启动时间

    int roomNumStartPersecond = 200; //每秒启动数量
}