package com.ssm.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssm.demo.entity.User;
import com.ssm.demo.util.PagedResult;

public interface IUserService {
	public User getUserById(int userId); 
	public List<User> selectAllUser();
	public void addUser(User user);
	public User selectByUser(User user);
	
	public PagedResult<User> queryByPage(Integer pageNo,Integer pageSize);
	
	public List<String> importData(MultipartFile file);
}
