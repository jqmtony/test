package com.ssm.demo.lucene;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.search.IndexSearcher;

public class LuceneUtil {
	 //项目启动的时候创建,关闭的时候销毁  
    private  IndexWriter indexWriter=null;  
    private  IndexSearcher indexSearcher=null;  
    private ConfigureLucene configureLucene=null;  
    public void setConfigureLucene(ConfigureLucene configureLucene) {  
        this.configureLucene = configureLucene;  
    }  
      
   public void init(){  
        try {  
            indexWriter=new IndexWriter(configureLucene.getDir(), configureLucene.getAna(), MaxFieldLength.LIMITED);  
            //在项目销毁的时候关闭indexWriter,每个应用程序对应一个Runtime  
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            throw new RuntimeException(e);  
        }  
    }  
   public void destory(){  
       try {  
            System.out.println("--资源销毁的代码在此处执行--");  
            indexWriter.close();  
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            throw new RuntimeException(e);  
        }  
   }  
    /** 
     * 在用的时候取indexWriter，关闭IndexSearcher，因为我们知道如果不关闭IndexSearcher，下次取得IndexSearcher是从内存中取得，并没有同步到索引库 
     * 因此会导致我们刚插入的数据在用IndexSearcher查询的时候会查询不得到刚插入的数据 
     * @return 
     */  
    public   IndexWriter getIndexWriter() {  
        closeIndexSearcher();//关键代码  
        return indexWriter;  
    }  
    public   IndexSearcher getIndexSearcher() {  
        //作用: 避免其他线程等待，意思是指如果有一个线程执行到创建indexSearcher之后，那么下一个或者多个线程就不用在进入到synchronized里面  
        if(indexSearcher==null){  
            synchronized (LuceneUtil.class) {//如果没有其他线程创建了indexSearcher，只允许一个线程进入到里面创建  
                if(indexSearcher==null){//作用：是否需要创建indexSearcher  
                    try {  
                        indexSearcher=new IndexSearcher(configureLucene.getDir());  
                    } catch (Exception e) {  
                        // TODO Auto-generated catch block  
                        throw new RuntimeException(e);  
                    }  
                }  
            }  
              
        }  
        return indexSearcher;  
    }  
    public   void closeIndexWriter(){  
        if(indexWriter!=null){  
            try {  
                indexWriter.close();  
                indexWriter=null;  
            } catch (Exception e) {  
                // TODO Auto-generated catch block  
                throw new RuntimeException(e);  
            }  
        }  
    }  
    public  void closeIndexSearcher(){  
        if(indexSearcher!=null){  
            try {  
                indexSearcher.close();  
                indexSearcher = null;  
            } catch (Exception e) {  
                // TODO Auto-generated catch block  
                throw new RuntimeException(e);  
            }  
        }  
    }  
}
