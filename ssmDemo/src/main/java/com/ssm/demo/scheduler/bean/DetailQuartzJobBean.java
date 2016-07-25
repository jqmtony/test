package com.ssm.demo.scheduler.bean;

import java.lang.reflect.Method;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean.StatefulMethodInvokingJob;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

/**
 * 集群情况下,通过该类调度实际的任务类
 * <p>
 */
public class DetailQuartzJobBean extends StatefulMethodInvokingJob {

  /**
   * Spring Context Key
   */
  public final static String APPLICATION_CONTEXT_KEY = "applicationContextKey";
  /**
   * 实际任务类的bean名称
   */
  public final static String TARGET_BEAN = "targetBean";
  /**
   * 任务类需要执行的方法
   */
  public final static String TARGET_METHOD = "targetMethod";
  /**
   * 实际任务类的bean名称
   */
  private Class<?> targetClass;
  /**
   * 任务类需要执行的方法
   */
  private String targetMethod;

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    try {
      // 获取Spring Application Context
      ApplicationContext applicationContext = (ApplicationContext) context.getScheduler().getContext()
          .get(APPLICATION_CONTEXT_KEY);
      // 代理的对象
      Object targetObject;
      // 定时调度的方法
      Method method;
      String targetBean = context.getMergedJobDataMap().getString(TARGET_BEAN);
      String targetMethod = context.getMergedJobDataMap().getString(TARGET_METHOD);
      if (StringUtils.hasText(targetBean)) {
        targetObject = applicationContext.getBean(targetBean);
        method = ReflectionUtils.findMethod(targetObject.getClass(), targetMethod);
      } else {
        targetObject = applicationContext.getBean(targetClass);
        method = ReflectionUtils.findMethod(targetClass, this.targetMethod);
      }
      ReflectionUtils.makeAccessible(method);
      ReflectionUtils.invokeMethod(method, targetObject);
    } catch (Exception e) {
      throw new JobExecutionException(e);
    }
  }

  public void setTargetClass(String targetClass) throws ClassNotFoundException {
    this.targetClass = Class.forName(targetClass);
  }

  public void setTargetMethod(String targetMethod) {
    this.targetMethod = targetMethod;
  }
}