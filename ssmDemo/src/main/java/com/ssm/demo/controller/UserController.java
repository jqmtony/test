package com.ssm.demo.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ssm.demo.entity.User;
import com.ssm.demo.service.IUserService;
import com.ssm.demo.util.PagedResult;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


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
//		List<User> userList = userService.selectAllUser();
//		map.put("userList", userList);
//		PagedResult<User>  pagedResult = userService.queryByPage(1,10);
//		JSONObject jsonObj = null;
//		if(pagedResult != null){
//		    JsonConfig jsonConfig = new JsonConfig(); 
//		    jsonObj = JSONObject.fromObject(pagedResult, jsonConfig);
//		}
//		map.put("userList", jsonObj.toString());
		return "User/userlist";
    }
	
	@RequestMapping("/showUsers")
	@ResponseBody
	public String showUsers(HttpServletRequest request,ModelMap map,Integer pageNumber,Integer pageSize,String userName){
//		List<User> userList = userService.selectAllUser();
//		map.put("userList", userList);
		PagedResult<User>  pagedResult = userService.queryByPage(pageNumber,pageSize);
		JSONObject jsonObj = null;
		if(pagedResult != null){
		    JsonConfig jsonConfig = new JsonConfig(); 
		    jsonObj = JSONObject.fromObject(pagedResult, jsonConfig);
		}
		map.put("userList", jsonObj.toString());
		return jsonObj.toString();
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
	
	@RequestMapping("/uploadUserList")
	public String uploadUser(HttpServletRequest request,ModelMap map){;
		return "Upload/upload";
    }
	
	@RequestMapping("/upload")
	@ResponseBody
	public List<String> upload(HttpServletRequest request, HttpServletResponse response,
	     @RequestParam("file") CommonsMultipartFile file) throws IOException {
		 List<String> messageList = null;
		 //异步线程
		 new Thread() {
		      @Override
		      public void run() {
		    	  List<String> messageList = userService.importData(file);
		      }
		    }.start();
		 return messageList;
	    }
}

