package com.hxiaol.demo.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMain {

    public static void main(String[] args) {
        String regex="[a-z\\-]+([0-9]+)";
        String targetString="dd22dd";
        //构造正则表达式
        Pattern p=Pattern.compile(regex); 
        //进行匹配
        Matcher m=p.matcher(targetString); 
        //是否完全匹配成功，要求与正则表达式完全匹配,相当于正则表达式自动加上^,结尾加上$
        System.out.println("match result "+m.matches());
        //上面代码等价于
        System.out.println("match result "+Pattern.matches(regex, targetString));
        
        //需要重置访问位置
        m.reset();
        //是否能够找到一个子串匹配上
        if (m.find( )) {
            //返回dd22,表示返回匹配的整个串
            System.out.println("Found value: " + m.group(0) );
            //返回22,返回匹配中第一个括号中的数据
            System.out.println("Found value: " + m.group(1 ));
            //1,只有一个括号，只返回1，不是2.
            System.out.println("groupCount value: " + m.groupCount());
            
         } else {
            System.out.println("NO MATCH");
         }
        
        
        
        

    }

}
