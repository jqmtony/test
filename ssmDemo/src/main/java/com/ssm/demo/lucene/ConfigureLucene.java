package com.ssm.demo.lucene;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class ConfigureLucene {
    //创建索引库  
    private  Directory dir=null;  
    //创建分词器  
    private  Analyzer ana=null;  
    public void init(){  
        //根据指定的路径创建索引库，如果路径不存在就会创建  
        try {  
            dir=FSDirectory.open(new File("c:/demo"));  
            //不同的分词器的版本不同，分词的算法不同，StandardAnalyzer只适用于英文  
            //ana=new StandardAnalyzer(Version.LUCENE_30);  
            ana=new IKAnalyzer(true);//使用最大词长分词  
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            throw new RuntimeException(e);  
        }  
    }  
    public  Directory getDir() {  
        return dir;  
    }  
    public  Analyzer getAna() {  
        return ana;  
    }  
}
