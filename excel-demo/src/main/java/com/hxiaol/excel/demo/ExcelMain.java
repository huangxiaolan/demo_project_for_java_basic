package com.hxiaol.excel.demo;

import java.io.File;
import java.util.List;

import org.apache.poi.openxml4j.util.ZipSecureFile.ThresholdInputStream;

public class ExcelMain {

    public static void main(String []args) {
        
        File file = new File(ExcelMain.class.getClassLoader().getResource("test.xlsx").getFile());  
        List<List<Object>> result = ExcelUtil.readExcel(file);  
        for(int i = 0 ;i < result.size() ;i++){  
            for(int j = 0;j<result.get(i).size(); j++){  
                System.out.println(i+"行 "+j+"列  "+ result.get(i).get(j).toString());  
            }  
        }  
        
        ExcelUtil.writeExcel(result,ExcelMain.class.getClassLoader().getResource("").getPath()+"tmp---write.xlsx"); 
    }
}
