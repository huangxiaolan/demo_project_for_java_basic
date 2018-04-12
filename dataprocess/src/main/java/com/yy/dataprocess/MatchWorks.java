package com.yy.dataprocess;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MatchWorks implements Runnable{
    
    int step;
    int first;
    List<String> keys;
    List<String> words;
    ResultForCountWord resultForCountWord;
    CountDownLatch countDownLatch;
    public  MatchWorks(int step,int first,List<String> keys,List<String> words,ResultForCountWord resultForCountWord,CountDownLatch countDownLatch) {
        this.step=step;
        this.first=first;
        this.keys=keys;
        this.words=words;
        this.resultForCountWord=resultForCountWord;
        this.countDownLatch=countDownLatch;
    }

    @Override
    public void run() {
      //遍历获取词根的词频
        for(int i=first;i<keys.size();i+=step) {
            int keyCount=0;
            String key=keys.get(i);
            for(String word : words) {
                if(CountWord.isKeyInWord(key,word)) {
                    keyCount++; 
                }
            }
            resultForCountWord.add(keyCount,key);
        }
        countDownLatch.countDown();
        
    }

}
