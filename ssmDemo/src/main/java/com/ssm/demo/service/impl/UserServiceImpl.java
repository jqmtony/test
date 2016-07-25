package com.ssm.demo.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.demo.dao.IUserDao;
import com.ssm.demo.entity.User;
import com.ssm.demo.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	private IUserDao userDao;
	@Override
	public User getUserById(int userId) {
		// TODO Auto-generated method stub
		return userDao.selectByPrimaryKey(userId);
	}
	@Override
	public List<User> selectAllUser(){
		return userDao.selectAllUser();
	}
	
	@Override
	public void addUser(User user){
		userDao.insert(user);
	}
	
	@Override
	public User selectByUser(User user){
		return userDao.selectByUser(user);
	}
}