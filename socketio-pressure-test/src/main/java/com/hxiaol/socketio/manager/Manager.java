package com.hxiaol.socketio.manager;

import com.hxiaol.socketio.unit.Unit;

/**
 *  压测活动的管理接口
 */
public interface Manager {
    /**
     * 添加压测的单元
     * @param unit
     */
    public void addUnit(Unit unit);

    /**
     * 启动压测过程
     */
    public void start();

    /**
     * 停止压测
     */
    public void stop();
}