package com.ssm.demo.service;

import java.util.List;

import com.ssm.demo.entity.User;

public interface ILuceneService {

	public void addDocument(User goods);
	
	public List<User> queryGoods(String keyword);
	
	public void deleteDocument(int id);
	
	public void updateDocument(User goods);
	
	public List<User> queryByPage(String name,int currentPage);
}
