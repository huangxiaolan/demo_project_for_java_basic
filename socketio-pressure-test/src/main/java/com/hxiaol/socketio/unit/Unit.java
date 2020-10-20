package com.hxiaol.socketio.unit;

import com.hxiaol.socketio.event.Event;

/**
 *  压测的单元
 */
public interface Unit {

    /**
     *  添加单元内的事件
     * @param event
     */
    public void addEvent(Event event);

    /**
     * 启动压测
     */
    public void start();

    /**
     * 停止压测
     */
    public void stop();

    /**
     * 压测是否停止
     * @return
     */
    public boolean isStop();
}