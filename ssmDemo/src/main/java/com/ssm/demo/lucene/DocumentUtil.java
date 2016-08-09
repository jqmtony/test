package com.ssm.demo.lucene;


import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;

import com.ssm.demo.entity.User;

public class DocumentUtil {
	private DocumentUtil(){}
	/**
	 * 把goods对象转为document对象
	 */
	public static Document goodsToDocument(User goods){
		//把goods对象转为document
		Document doc=new Document();
		doc.add(new Field("id", goods.getId().toString(), Store.YES, Index.NOT_ANALYZED));
		doc.add(new Field("userName", goods.getUserName(), Store.YES, Index.ANALYZED));
		doc.add(new Field("password", goods.getPassword().toString(), Store.YES, Index.NOT_ANALYZED));
		doc.add(new Field("age", goods.getAge().toString(), Store.YES, Index.ANALYZED));
		return doc;
	}
	
	/**
	 * 把document对象转为goods对象
	 */
	public static User documentToGoods(Document doc){
		User goods=new User();
		goods.setId(Integer.parseInt(doc.get("id")));
		goods.setUserName(doc.get("userName"));
		goods.setPassword(doc.get("password"));
		goods.setAge(Integer.parseInt(doc.get("age")));
		return goods;
	}
}
