package com.hxiaol.demo.lombok;

import java.io.File;
import java.io.FileOutputStream;

import com.hxiaol.demo.lombok.bean.User;

import lombok.Cleanup;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( new User("name","phone") );
        
        File file=new File(App.class.getClassLoader().getResource("demo.txt").getFile());
        try {
            @Cleanup FileOutputStream fileOutputStream=new FileOutputStream(file);
            fileOutputStream.write("hello".getBytes("utf-8"));
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }
}
