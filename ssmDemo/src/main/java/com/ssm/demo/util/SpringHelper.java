package com.ssm.demo.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

/**
 * spring工具类, 用于在非Spring管理的对象中,获取Spring管理的Bean
 *
 */
public class SpringHelper implements ApplicationContextAware {

  private static ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    SpringHelper.applicationContext = applicationContext;
  }

  /**
   * 根据类名获取Bean
   *
   * @param clasz
   *
   * @return
   */
  public static <T> T getBean(Class<T> clasz) {
    return SpringHelper.applicationContext.getBean(clasz);
  }

  /**
   * 根据名称获取Bean
   *
   * @param beanName
   *
   * @return
   */
  @SuppressWarnings("unchecked")
  public static <T> T getBean(String beanName) {
    return (T) SpringHelper.applicationContext.getBean(beanName);
  }

  public static Resource getResource(String path) {
    return SpringHelper.applicationContext.getResource(path);
  }

  public static ApplicationContext getApplicationContext() {
    return SpringHelper.applicationContext;
  }
}
