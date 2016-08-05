package com.ssm.demo.service.impl;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.ssm.demo.dao.IUserDao;
import com.ssm.demo.entity.User;
import com.ssm.demo.service.ITemplateAnalyzeSerivce;
import com.ssm.demo.service.IUserService;
import com.ssm.demo.util.BeanUtil;
import com.ssm.demo.util.PagedResult;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	private IUserDao userDao;
	@Autowired
	private ITemplateAnalyzeSerivce templateAnalyzeSerivce;
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
	
	public PagedResult<User> queryByPage(Integer pageNo,Integer pageSize) {  
	    pageNo = pageNo == null?1:pageNo;  
	    pageSize = pageSize == null?10:pageSize;  
	    PageHelper.startPage(pageNo,pageSize);  //startPage是告诉拦截器说我要开始分页了。分页参数是这两个。  
	    return BeanUtil.toPagedResult(userDao.selectAllUser());  
	} 
	
	  @Override
	  @Transactional
	  public List<String> importData(MultipartFile file) {
	    // TODO Auto-generated method stub
	    List<String> messageList = new ArrayList<String>();
	    // 验证文件格式
	    if (!templateAnalyzeSerivce.validationEXl(file)) {
	      // 该文件格式不对
	      messageList.add("This format of the file is not correct");
	      return messageList;
	    }
	    List<Object[]> list = null;
	    try {
	        if (file.getOriginalFilename().endsWith("xls")) {
	            list = templateAnalyzeSerivce.converExlToList(file.getInputStream());
	          } else if (file.getOriginalFilename().endsWith("xlsx")) {
	            list = templateAnalyzeSerivce.converXlsxToList(file.getInputStream());
	          }
	    } catch (IOException e1) {
	      // TODO Auto-generated catch block
	      e1.printStackTrace();
	      messageList.add("Upload error");
	      return messageList;
	    }
	    if (null == list || list.size() < 1) {
	      // 导入数据为空
	      messageList.add("Import data is empty.");
	      return messageList;
	    }
	    int n = 0;
	    for (n = 1; n < list.size(); n++) {
	      try {
	        Object[] listItem = null;
	        listItem = list.get(n);
	        if (listItem[0].toString().length() < 0) {
	            messageList.add("Line " + (n + 1) + " is wrong.");
	          }
	        
	        User user = new User();
	        user.setUserName(listItem[0].toString());
	        user.setPassword(listItem[1].toString());
	        user.setAge(Integer.parseInt(listItem[2].toString()));
	        userDao.insert(user);
	        
	        if(!messageList.isEmpty()){
	        	 throw new RuntimeException();
	        }
	      }catch (Exception e) {
	          TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//注解事务不起作用，手动将事务回滚
	      } 
	    }
	    return messageList;
	  }
}