package com.ssm.demo.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ssm.demo.entity.User;
import com.ssm.demo.service.IUserService;
import com.ssm.demo.util.PagedResult;



@RunWith(SpringJUnit4ClassRunner.class)		//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})

public class TestMyBatis {
	private static Logger logger = Logger.getLogger(TestMyBatis.class);
	@Autowired
	private IUserService userService;


	@Test
	public void test1() {
//		User user = userService.getUserById(1);
//		logger.info(user);
		PagedResult<User>  pagedResult = userService.queryByPage(1,10);//null表示查全部    
	    logger.debug("查找Data List结果"+pagedResult.getDataList());  
	    System.out.println("查找Data List结果"+pagedResult.getDataList());
	}
}
