package com.ssm.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssm.demo.entity.User;
import com.ssm.demo.service.IUserService;


@Controller
@RequestMapping("/User")
public class UserController {
	@Autowired
	private IUserService userService;
	
	@RequestMapping("/showUser")
	public String toIndex(HttpServletRequest request,Model model){
		int userId = Integer.parseInt(request.getParameter("id"));
		User user = userService.getUserById(userId);
		model.addAttribute("user", user);
		return "showUser";
	}
	
	@RequestMapping("/showAllUser")
	public String showAllUser(HttpServletRequest request,ModelMap map){
		List<User> userList = userService.selectAllUser();
		map.put("userList", userList);
		return "User/userlist";
    }
	
	@RequestMapping("/addUser")
	public String addUser(HttpServletRequest request,ModelMap map,User user,
		   String userName, String password, Integer age){
		user.setPassword(password);
		user.setAge(age);
		user.setUserName(userName);
        userService.addUser(user);
        List<User> userList = userService.selectAllUser();
		map.put("userList", userList);
		return "User/userlist";
    }
	
}

