package com.yy.dataprocess;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class WordProcesssUtil {
    static Pattern p=Pattern.compile("[a-z\\s\\-]+"); 
    
    public static void main(String args[]) {
        String word="adW";
        System.out.println(word+" is "+ isSuitable(word));
        
        word="ad -";
        System.out.println(word+" is "+ isSuitable(word));
        
        word="xd~";
        System.out.println(word+" is "+ isSuitable(word));
        
        word="abcdef";
        System.out.println(word+" 's subword is "+ getAllSubwords(word));
        
        word="abc def- sfd";
        System.out.println(word+" 's subword is "+ getAllSubwords(word));
        
        String word1="abdf";
        String word2="abcd";
        System.out.println("word1:"+word1+",word2:"+word2+" :edit distance is "+StringUtils.getLevenshteinDistance(word1, word2));
    }
    //字母转换为小写字母
    public static List<String> getLowerCaseWords(List<String> words){
        return words.stream().map(action->action.toLowerCase().trim()
                   ).collect(Collectors.toList());
    }
    
    //获取长度大于4的词列表
    public static List<String> getWordsLonger4(List<String> words){
        return words.stream().filter(predicate-> predicate.length()>4)
                   .collect(Collectors.toList());
    }
    
    public static Set<String> getAllSubwords(String words){
        
        Set<String> subwords=new HashSet<>();
        if(words==null || words.length()<2) {
            return subwords;
        }
        //先分割
        String [] splits=words.split("-| ");
        
        //然后取所有长度为2-6的子串
        for(String word : splits) {
            if(word==null || word.length() <2) {
                continue;
            }
            int length=word.length();
            for(int i=0;i<length-1;i++) {
                for(int j=2;j<7;j++) {
                    if(i+j<length+1) {
                        subwords.add(word.substring(i, i+j));
                    }
                }
            }
        }
        return subwords;
    }
    
    //判断词语是否合法
    public static boolean isSuitable(String word) {

        
        //校验是否有特殊字符
        
        Matcher m=p.matcher(word); 
        return m.matches();
        
    }
}
