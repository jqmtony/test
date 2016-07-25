package com.ssm.demo.scheduler.bean;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;


/**
 * 扫描{@link Quartz}注解,并注册定时任务到Quartz调度器中
 * 
 *
 */
public class QuartzAnnotationBeanPostProcessor implements BeanPostProcessor, Ordered, ApplicationContextAware,
    ApplicationListener<ContextRefreshedEvent>, DisposableBean {

  private static final Log logger = LogFactory.getLog(QuartzAnnotationBeanPostProcessor.class);

  private ApplicationContext applicationContext;

  @Autowired
  private Scheduler scheduler;

  private final Set<Class<?>> nonAnnotatedClasses = Collections
      .newSetFromMap(new ConcurrentHashMap<Class<?>, Boolean>(64));

  @Override
  public int getOrder() {
    return LOWEST_PRECEDENCE;
  }

  /**
   * Setting an {@link ApplicationContext} is optional: If set, registered tasks
   * will be activated in the {@link ContextRefreshedEvent} phase; if not set,
   * it will happen at {@link #afterSingletonsInstantiated} time.
   */
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    if (event.getApplicationContext() == this.applicationContext) {
      // Running in an ApplicationContext -> register tasks this late...
      // giving other ContextRefreshedEvent listeners a chance to perform
      // their work at the same time (e.g. Spring Batch's job registration).
      try {
        if (!scheduler.isStarted()) {
          scheduler.start();
        }
      } catch (SchedulerException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) {
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(final Object bean, String beanName) {

    Class<?> targetClass = AopUtils.getTargetClass(bean);
    if (!this.nonAnnotatedClasses.contains(targetClass)) {
      final Set<Method> annotatedMethods = new LinkedHashSet<>(1);
      // 扫描类中的方法,并对添加了Quartz注解的方法进行处理
      ReflectionUtils.doWithMethods(targetClass, method -> {
        Quartz quartz = AnnotationUtils.getAnnotation(method, Quartz.class);
        if (quartz != null) {
          processScheduled(quartz, method, beanName);
          annotatedMethods.add(method);
        }
      });
      if (annotatedMethods.isEmpty()) {
        this.nonAnnotatedClasses.add(targetClass);
        if (logger.isTraceEnabled()) {
          logger.trace("No @Quartz annotations found on bean class: " + bean.getClass());
        }
      } else {
        if (logger.isDebugEnabled()) {
          logger.debug(
              annotatedMethods.size() + " @Quartz methods processed on bean '" + beanName + "': " + annotatedMethods);
        }
      }
    }
    return bean;
  }

  /**
   * 根据注解,将方法注册为定时任务
   */
  private void processScheduled(Quartz quartz, Method method, String beanName) {
    try {
      Assert.isTrue(void.class == method.getReturnType(), "Only void-returning methods may be annotated with @Quartz");
      Assert.isTrue(method.getParameterTypes().length == 0, "Only no-arg methods may be annotated with @Quartz");

      String jobName = quartz.name().isEmpty() ? beanName : quartz.name();

      JobKey jobKey = new JobKey(jobName, quartz.group());
      TriggerKey triggerKey = new TriggerKey(jobName, quartz.group());
      JobDataMap jobDataMap = new JobDataMap();
      jobDataMap.put(DetailQuartzJobBean.TARGET_BEAN, beanName);
      jobDataMap.put(DetailQuartzJobBean.TARGET_METHOD, method.getName());
      JobDetail jobDetail = JobBuilder.newJob(DetailQuartzJobBean.class).setJobData(jobDataMap).withIdentity(jobKey)
          .storeDurably().build();
      TimeZone timezone = quartz.zone().isEmpty() ? TimeZone.getDefault() : TimeZone.getTimeZone(quartz.zone());
      CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
          .withSchedule(CronScheduleBuilder.cronSchedule(quartz.cron()).inTimeZone(timezone)
              .withMisfireHandlingInstructionDoNothing())
          .forJob(jobDetail).withPriority(quartz.priority()).withDescription(quartz.desc()).build();
      scheduler.addJob(jobDetail, true);
      if (scheduler.checkExists(triggerKey)) {
        scheduler.rescheduleJob(triggerKey, trigger);
      } else {
        scheduler.scheduleJob(trigger);
      }
    } catch (SchedulerException e) {
      throw new RuntimeException("定时任务处理失败, beanName:" + beanName + ", method:" + method.toString(), e);
    }
  }

  @Override
  public void destroy() throws SchedulerException {
    scheduler.shutdown();
  }

}