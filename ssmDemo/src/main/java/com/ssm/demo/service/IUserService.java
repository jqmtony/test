package com.ssm.demo.service;

import java.util.List;

import com.ssm.demo.entity.User;

public interface IUserService {
	public User getUserById(int userId); 
	public List<User> selectAllUser();
	public void addUser(User user);
	public User selectByUser(User user);
}
