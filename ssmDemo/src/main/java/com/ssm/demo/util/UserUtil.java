package com.ssm.demo.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ssm.demo.entity.User;

/**
 * 用户工具类
 * 
 * @author gaowei16
 *
 */
public class UserUtil {
  private static final Logger logger = LoggerFactory.getLogger(UserUtil.class);

  /**
   * 避免创建该类
   */
  private UserUtil() {

  }

  /**
   * 获取当前的登录用户
   * 
   * @return
   */
  public static User getCurLoginUser() {
    Subject curSubject = SecurityUtils.getSubject();
    return (User) curSubject.getPrincipal();
  }

  /**
   * 获取当前用户
   * <p>
   * 如果登录,则返回登录用户;否则返回系统默认用户{@link com.lenovo.lclaim.entity.SysUser#SYSTEM_USER}
   * </p>
   * 
   * @return
   */
  public static User getCurUser() {
    // 后台任务,调用getSecurityManager方法会抛出异常,此时操作用户为SYSTEM_USER
    try {
      SecurityUtils.getSecurityManager();
    } catch (Exception e) {
      logger.debug("获取当前用户失败", e);
      return null;
    }
    Subject curSubject = SecurityUtils.getSubject();
    if (curSubject.isAuthenticated()) {
      return (User) curSubject.getPrincipal();
    } else {
      return null;
    }

  }
}