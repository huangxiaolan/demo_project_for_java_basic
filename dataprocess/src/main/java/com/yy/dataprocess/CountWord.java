package com.yy.dataprocess;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;


public class CountWord {
    
    public static String sourceFileName="D:/workdocument/wroot.txt";
    //public static String sourceFileName="D:/workdocument/wroot_part.txt";
    

    public static void main(String [] args) {
        CountWord countWord=new CountWord();
        List<String> words=countWord.getWords();
        List<String> keys=countWord.getInitCountResult(words);
//        List<String> keys=new ArrayList<>();
//        keys.add("pidi");
//        keys.add("alinea");
        print("词根的个数为："+keys.size());
        
        ResultForCountWord resultForCountWord=new ResultForCountWord();
        
        List<String> matchWords=WordProcesssUtil.getWordsLonger4(words);
        
        int threadNum=20;
        
        ExecutorService executorService=Executors.newFixedThreadPool(threadNum);
        CountDownLatch countDownLatch=new CountDownLatch(threadNum);
        for(int i=0;i<threadNum;i++) {
            executorService.execute(new MatchWorks(20, i, keys, matchWords, resultForCountWord,countDownLatch));
        }
        
//        //遍历获取词根的词频
//        for(String key : keys) {
//            int keyCount=0;
//            
//            for(String word : matchWords) {
//                if(countWord.isKeyInWord(key,word)) {
//                    keyCount++; 
//                }
//            }
//            resultForCountWord.add(keyCount,key);
//        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        resultForCountWord.output();
    }
    
    //计算词根是否用单词的子串做一次变换可以得到
    public static boolean isKeyInWord(String key, String word) {
        int keyLength=key.length();
        Set<String> subwords=WordProcesssUtil.getAllSubwords(word);
        for(String subword:subwords) {
            int lengthDiff=subword.length()-keyLength;
            if(lengthDiff>=-1 && lengthDiff<=1) {
                if(StringUtils.getLevenshteinDistance(subword, key)<=1) {
                    return true;
                }
            }
            
        }
        return false;
    }
    //返回词语列表
    public List<String> getWords() {
        List<String> list=new ArrayList<String>();
        
        File file=new File(sourceFileName);
        try {
            list=FileUtils.readLines(file);
        } catch (IOException e) {
            
            throw new RuntimeException("无法读取文件数据，fileName:"+sourceFileName);
        }
        
        print("成功读取文件，读取字符个数是:"+list.size() );
        //字母转换为小写
        list=WordProcesssUtil.getLowerCaseWords(list);
        
        //校验是否有特殊字符
        for(String word: list) {
            
            if(!WordProcesssUtil.isSuitable(word)) {
                print("检测到非法字符："+word);
            };
        }
        return list;
    }
    //返回初始的词根
    public List<String> getInitCountResult(List<String> words){
        
        //找到长度大于2而小于6的字符串作为词根
        return words.stream().filter(predicate -> predicate.length()>=2 && predicate.length()<=6).collect(Collectors.toList());
    }
    
    //输出调试信息
    public static void print(String value) {
        System.out.println(value);
    }
}
