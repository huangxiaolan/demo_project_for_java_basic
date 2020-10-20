package com.hxiaol.false_sharing_test.data;

/**
 * 测试数据类
 * @author hxl
 * 2018年5月15日
 *
 */
public class TestDataBean {

    
    volatile int x;
    
    volatile int y;
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
}
