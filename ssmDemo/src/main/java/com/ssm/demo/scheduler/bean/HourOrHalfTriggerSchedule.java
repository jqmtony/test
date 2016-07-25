package com.ssm.demo.scheduler.bean;
import org.springframework.stereotype.Component;

@Component
public class HourOrHalfTriggerSchedule {
  /*
   * 定时扫描
   */
  public void TestJobCheck() {
	System.out.println("定时任务启动");
  }

}
