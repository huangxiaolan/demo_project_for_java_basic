package com.hxiaol.false_sharing_test;

import com.hxiaol.false_sharing_test.data.TestDataBean;

/**
 * Hello world!
 *
 */
public class App implements Runnable
{
    public App(TestDataBean testDataBean,Boolean setX,String name) {
        this.testDataBean=testDataBean;
        this.name=name;
        this.setX=setX;
    }
    //执行次数
    static int count = 10000000;
    
    TestDataBean testDataBean;
    
    boolean setX=true;
    
    long startTime;
    
    long endTime;
    
    String name ;
    
    public static void main( String[] args )
    {
        TestDataBean testDataBean=new TestDataBean();
        App app1=new App(testDataBean,true,"test-x-simple");
        App app2=new App(testDataBean,false,"test-y-simple");
        
        App app3=new App(testDataBean,true,"test-x-muti");
        
        App app4=new App(testDataBean,false,"test-y-muti");
        app1.run();
        
        app2.run();
        
        new Thread(app3).start();
        
        new Thread(app4).start();
    }

    public void run() {
        startTime=System.currentTimeMillis();
        if(setX) {
            
            for(int i=0;i<count;i++) {
                testDataBean.setX(i);
            }
            
        }else {
            for(int i=0;i<count;i++) {
                testDataBean.setY(i);
            }
        }
        endTime=System.currentTimeMillis();
        
        output();
    }
    
    public void output() {
        System.out.println(name+" execute time is "+(endTime-startTime)+" ms");
    }
}
