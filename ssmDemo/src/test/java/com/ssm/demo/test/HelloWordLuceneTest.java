//package com.ssm.demo.test;
//
//
//import java.util.List;
//
//import org.junit.AfterClass;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import com.ssm.demo.entity.User;
//import com.ssm.demo.lucene.HelloWordLucene;
//
//public class HelloWordLuceneTest {
//
//	private static HelloWordLucene hellowod;
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		hellowod=new HelloWordLucene();
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//		hellowod=null;
//	}
//
////	@Test
////	public void testAddDocument() {
////		User goods=new User();
////		goods.setId(7);
////		goods.setUserName("xiao");
////		goods.setPassword("xk");
////		goods.setAge(11);
////		hellowod.addDocument(goods);
////	}
//	
////    @Test  
////    public void testdelete() {  
////         hellowod.deleteDocument(7);  
////    }  
////      
////    @Test  
////    public void testupdate() {  
////        User goods=new User();   
////        goods.setId(7);
////		goods.setUserName("xiao");
////		goods.setPassword("xk11");
////		goods.setAge(11);
////        hellowod.updateDocument(goods);  
////    }  
////    
////	@Test
////	public void testquery() {
////		List<User> list= hellowod.queryGoods("xiao");
////		for(User good:list){
////			System.out.println("用户编号："+good.getId()+",用户名称："+good.getUserName()+
////					",用户密码："+good.getPassword()+",用户年龄："+good.getAge()
////					);
////		}
////	}
//	
//	/** 
//     * 先查询，在插入，然后在查询  
//     */  
////    @Test  
////    public void test9() {  
////        hellowod.queryGoods("ibm");  
////        System.out.println("----------------->");  
////        User goods=new User();  
////        goods.setId(12);  
////        goods.setUserName("ibm");  
////        goods.setPassword("xiaokun");  
////        goods.setAge(11);  
////        hellowod.addDocument(goods);  
////        System.out.println("----------------->");  
////        hellowod.queryGoods("ibm");  
////    }  
//     
//    /** 
//     * 测试中文分词器IKAnalyzer3.2.8添加 
//     */  
////    @Test  
////    public void test8() {  
////        User goods=new User();  
////        goods.setId(11);  
////        goods.setUserName("IBM是全球知名的电脑厂商，现在已经专门做服务的，已经把生产版权转给联想了，用户名电脑");  
////        goods.setPassword("23we");  
////        goods.setAge(34);  
////        hellowod.addDocument(goods);  
////    }  
//      
//    /** 
//     * 测试中文分词器IKAnalyzer3.2.8的检索 
//     */  
////    @Test  
////    public void test10() {  
////        List<User> list= hellowod.queryGoods("23we");  
////        for(User good:list){  
////            System.out.println("用户编号："+good.getId()+",用户名称："+good.getUserName()+  
////                    ",用户密码："+good.getPassword()+",用户年龄："+good.getAge()  
////                    );  
////        }  
////    }  
//	
//	
//  @Test  
//  public void test10() {  
//      List<User> list= hellowod.queryByPage("23we",2);  
//      for(User good:list){  
//          System.out.println("用户编号："+good.getId()+",用户名称："+good.getUserName()+  
//                  ",用户密码："+good.getPassword()+",用户年龄："+good.getAge()  
//                  );  
//      }  
//  } 
//}
