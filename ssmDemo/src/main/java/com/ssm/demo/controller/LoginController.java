package com.ssm.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public void login(HttpServletRequest request, ModelMap map) {
	 System.out.println("登录启动！"); 
	}

	 /**
	   * 用户登录
	   */
	  @RequestMapping(value = "/login", method = RequestMethod.POST)
	  public String login(ModelMap map, @RequestParam(required = false) String username,
	      @RequestParam(required = false) String password) {
	    if (username == null || username.trim().isEmpty()) {
	      map.put("username_error", "User Name Can not be blank");
	      return "login";
	    }
	    if (password == null || password.trim().isEmpty()) {
	      map.put("password_error", "Password can not be blank");
	      return "login";
	    }

	    UsernamePasswordToken token = new UsernamePasswordToken(username, password);
	    Subject currentUser = SecurityUtils.getSubject();
	    try {
	      // 验证前,将当前已经登录的用户退出
	      currentUser.logout();
	      currentUser.login(token);
	    } catch (AuthenticationException ae) {
	      LOGGER.error("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下", ae);
	    }
	    // 验证是否登录成功
	    if (currentUser.isAuthenticated()) {
	      LOGGER.info("对用户[" + username + "]进行登录验证..验证通过");
	      return "redirect:/index.do";
	    } else {
	      LOGGER.info("用户名或密码不正确");
	      map.put("message_login", "Incorrect username or password.");
	      token.clear();
	      return "login";
	    }
	  }
	/**
	 * 用户登出
	 */
	@RequestMapping("/logout")
	public String logout() {
		SecurityUtils.getSubject().logout();
		return "login";
	}
}
