package com.ssm.demo.scheduler.bean;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.quartz.Scheduler;
import org.quartz.Trigger;

/**
 * 注解在方法上以便 {@link QuartzAnnotationBeanPostProcessor} 扫描并在Quartz中注册定时任务
 * 
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Quartz {
  String name() default "";

  String group() default Scheduler.DEFAULT_GROUP;

  String cron();

  String zone() default "";

  int priority() default 0;

  int misfireInstruction() default Trigger.MISFIRE_INSTRUCTION_SMART_POLICY;

  String desc() default "";
}
