package com.yy.dataprocess;
/**
 * 频次的统计结果
 * @author Administrator
 *
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.EvaluationWorkbook.ExternalName;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ResultForCountWord {
    
    Map<String, Integer> results=new HashMap<>();
    
    private static String excelName="D:/workdocument/wroot_part.xlsx";
    
    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";
    
    int count=0;

    public  void  add(int num,String key) {
//        List<String> keys=results.get(num);
//        if(keys==null) {
//            keys=new ArrayList<String>();
//            keys.add(key);
//            results.put(num, keys);
//        }else {
//            keys.add(key);
//            count++;
//            if(count%100==0) {
//                System.out.println("num:"+num+"  key:"+key);
//            }
//        }
        results.put(key, num);
        if(count%300==0) {
            System.out.println("count:"+count+" key:"+key+":"+num);
        }
        count++;
    }
    
    public void output() {
//        for(Entry<Integer, List<String>> entry:results.entrySet()) {
//            System.out.println("length:"+entry.getKey()+",value:"+entry.getValue());
//        }
        writeExcel(results, results.size(), excelName);
    }
    
    public static void writeExcel(Map<String, Integer> dataList, int cloumnCount,String finalXlsxPath){
        OutputStream out = null;
        try {
            // 获取总列数
            int columnNumCount = cloumnCount;
            // 读取Excel文档
            File finalXlsxFile = new File(finalXlsxPath);
            Workbook workBook = getWorkbok(finalXlsxFile);
            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(0);
            /**
             * 删除原有数据，除了属性列
             */
            int rowNumber = sheet.getLastRowNum();    // 第一行从0开始算
            System.out.println("原始数据总行数，除属性列：" + rowNumber);
            for (int i = 1; i <= rowNumber; i++) {
                Row row = sheet.getRow(i);
                sheet.removeRow(row);
            }
            // 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out =  new FileOutputStream(finalXlsxPath);
            workBook.write(out);
            /**
             * 往Excel中写新数据
             */
            int j=0;
            for (Entry<String, Integer> entry : dataList.entrySet()) {
                // 创建一行：从第二行开始，跳过属性列
                
                Row row = sheet.createRow(j + 1);
                // 得到要插入的每一条记录
                
                
                // 在一行内循环
                Cell first = row.createCell(0);
                first.setCellValue(entry.getKey());
        
                Cell second = row.createCell(1);
                second.setCellValue(entry.getValue());
        
                j++;
            }
            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out =  new FileOutputStream(finalXlsxPath);
            workBook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if(out != null){
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("数据导出成功");
    }
    
    /**
     * 判断Excel的版本,获取Workbook
     * @param in
     * @param filename
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbok(File file) throws IOException{
        Workbook wb = null;
        FileInputStream in = new FileInputStream(file);
        if(file.getName().endsWith(EXCEL_XLS)){     //Excel&nbsp;2003
            wb = new HSSFWorkbook(in);
        }else if(file.getName().endsWith(EXCEL_XLSX)){    // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }
}
