package com.glyxybxhtxt.config;

import com.glyxybxhtxt.util.quartzUtils.OrderListener;
import com.glyxybxhtxt.util.quartzUtils.ShyCheck;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Date;

/**
 * Author:wangzh
 * Date: 2021/1/12 16:33
 * Version: 1.0
 */
@Configuration
public class QuartzConfiguration {
/*
定时任务配置包括  ：
    jobDetail 任务实例
    定时任务执行的具体内容等 -相关业务代码
    trigger 触发器
    设置相关的运行周期等    -绑定任务实例
    scheduler 调度器
    决定哪些定时任务会执行  -绑定触发器
*/

    /**
     * 定时任务 1
     */

    // 配置定时任务1的任务实例
    @Bean(name = "firstJobDetail")
    public MethodInvokingJobDetailFactoryBean firstJobDetail(OrderListener orderListener) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        // 是否并发执行
        jobDetail.setConcurrent(false);
        // 为需要执行的实体类对应的对象
        jobDetail.setTargetObject(orderListener);
        // 需要执行的方法
        jobDetail.setTargetMethod("executeGsCheck");
        return jobDetail;
    }


    // 配置触发器1
    @Bean(name = "firstTrigger")
    public CronTriggerFactoryBean firstTrigger(JobDetail firstJobDetail) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(firstJobDetail);
        // 设置任务启动延迟
        trigger.setStartDelay(0);
        // 设置定时任务启动时间
        trigger.setStartTime(new Date());
//        // 每5秒执行一次
//        trigger.setRepeatInterval(5000);
        //构建CronTrigger（触发器）实例,每天九点-十八点执行每隔20分钟执行一次
//         .withSchedule(CronScheduleBuilder.cronSchedule("0 0/20 9-18 * * ?"))
        trigger.setCronExpression("0 0/20 9-18 * * ?");
        return trigger;
    }


    /**
     * 定时任务 2
     *
     * @param shyCheck
     * @return
     */

    // 配置定时任务2的任务实例
    @Bean(name = "secondJobDetail")
    public MethodInvokingJobDetailFactoryBean secondJobDetail(ShyCheck shyCheck) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        // 是否并发执行
        jobDetail.setConcurrent(true);
        // 为需要执行的实体类对应的对象
        jobDetail.setTargetObject(shyCheck);
        // 需要执行的方法
        jobDetail.setTargetMethod("executeShyCheck");
        return jobDetail;
    }

    // 配置触发器2
    @Bean(name = "secondTrigger")
    public CronTriggerFactoryBean secondTrigger(JobDetail secondJobDetail) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(secondJobDetail);
        // 设置定时任务启动时间
        trigger.setStartTime(new Date());
        //构建CronTrigger（触发器）实例,早上8点-早上10点每隔15分钟执行一次
//                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/15 8-10 * * ?"))
        // cron表达式
        trigger.setCronExpression("0 0/15 8-10 * * ?");
        return trigger;
    }


    // 配置Scheduler
    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactory(Trigger firstTrigger, Trigger secondTrigger) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        // 延时启动，应用启动1秒后
        bean.setStartupDelay(1);
        // 注册触发器
        bean.setTriggers(firstTrigger, secondTrigger);
        return bean;
    }


}
