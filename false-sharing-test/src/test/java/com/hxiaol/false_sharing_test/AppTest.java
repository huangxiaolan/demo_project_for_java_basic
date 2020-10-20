package com.hxiaol.false_sharing_test;

/**
 * Unit test for simple App.
 */
public class AppTest 
   
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    
    public  static void main( String [] args)
    {
        int change[] = new int[]{1,1,1,1,1,1};
        System.out.println(String.format("第几回合    1到5的概率   本回合6出现的概率   到本回合结束6不出现的概率"));
        double left6=1.0;
        for(int i=1;i<8;i++) {
            int count = 5 + change[5];
            left6= left6 * 5 / count;
            System.out.println(String.format("%-4d %d/%d  %d/%d  %f", i,1,count, change[5] , count, 1-left6 ));
            change[5]=change[5]+1;
        }
    }

 
}
