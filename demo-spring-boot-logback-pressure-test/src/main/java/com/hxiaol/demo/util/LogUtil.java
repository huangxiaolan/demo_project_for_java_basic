package com.hxiaol.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  压测辅助工具
 */
@Slf4j
public class LogUtil {

    static Logger resultLogger = LoggerFactory.getLogger("result");

    static long i = 0;

    public static void logMessage(String str){
        log.info(str);
    }
    public static void logMessageTest(){
        logMessage("test" /*+ i++*/);
    }

    public static void logResult(String result){

        resultLogger.info(result);
    }
}