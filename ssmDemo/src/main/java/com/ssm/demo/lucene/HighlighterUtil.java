package com.ssm.demo.lucene;

import java.io.IOException;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

public class HighlighterUtil {
	 private Formatter formatter=null;  
	    private ConfigureLucene configureLucene=null;  
	    public void setConfigureLucene(ConfigureLucene configureLucene) {  
	        this.configureLucene = configureLucene;  
	    }  
	    public void init(){  
	        formatter=new SimpleHTMLFormatter("<font color='red'>", "</font>");  
	    }  
	    public  String setHighlighterText(Query query,String iniData,int length){  
	        String result=null;  
	        try {  
	            // query对象中有查询的关键字,字段匹配关键字的内容将会高亮  
	            Scorer scorer=new QueryScorer(query);  
	            // 实现高亮的工具类  
	            Highlighter highlighter=new Highlighter(formatter, scorer);  
	            // 设置高亮后的字符长度  
	            highlighter.setTextFragmenter(new SimpleFragmenter(length));  
	            //给指定字段进行高亮效果  
	            result= highlighter.getBestFragment(configureLucene.getAna(), null,iniData);  
	              
	            if(result==null){  
	                if(iniData.length()>length){  
	                    result=iniData.substring(0, length);  
	                }else{  
	                    result=iniData;  
	                }  
	            }  
	        } catch (Exception e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	        return result;  
	    }  
}
