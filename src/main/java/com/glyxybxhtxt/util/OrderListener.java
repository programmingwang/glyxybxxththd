package com.glyxybxhtxt.util;

import com.glyxybxhtxt.dataObject.Bxd;
import com.glyxybxhtxt.dataObject.Jdr;
import com.glyxybxhtxt.service.BxdService;
import com.glyxybxhtxt.service.EwmService;
import com.glyxybxhtxt.service.JdrService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:wangzh
 * Date: 2020/12/12 11:54
 * Version: 1.0
 * Description : 此类用来监听订单派单给总工时已经超过2的接单人，将这些单重新派给其他接单人
 */
@Slf4j
public class OrderListener implements Job {

    private BxdService bs = (BxdService) SpringContextUtil.getBean("bxdServiceImpl");

    private EwmService es = (EwmService) SpringContextUtil.getBean("ewmServiceImpl");

    private JdrService js = (JdrService) SpringContextUtil.getBean("jdrServiceImpl");

    private AutoOrder zdpd = (AutoOrder) SpringContextUtil.getBean("autoOrder");

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("正在监听接口。。。。。");
        //查找工时超过2的接单人的订单
        //1、查找状态为1，2,3的接单人
        List<Jdr> allJdr1and2 = new ArrayList<>();
        allJdr1and2.addAll(js.selalljdr("1"));
        allJdr1and2.addAll(js.selalljdr("2"));
        allJdr1and2.addAll(js.selalljdr("3"));
        //今天工时>=2的接单人
        List<Jdr> gsgt2 = new ArrayList<>();
        //当前接单人工时超过2的且状态为已派单（state=1）的工单
        List<Bxd> state1gd = new ArrayList<>();

        //查找今天总工时已经>=2的接单人
        for (Jdr jdr : allJdr1and2) {
            Double gs = bs.selgs(jdr.getYbid());
            gs = ObjectUtils.isEmpty(gs) ? 0 : gs ;
            if(gs >= Double.parseDouble("2")){
                //当前jdr总工时>=2，找到他还未完成的订单，重新派单给其他接单人
                Bxd b = new Bxd();
                b.setState(1);
                b.setJid(jdr.getYbid());
                //找到了所有未维修的订单
                state1gd.addAll(bs.selbxdbyjdr(b));
            }
        }

        //给这些单重新派单
        for (Bxd bxd : state1gd) {
            bxd.setJid(zdpd.zdpd(String.valueOf(bxd.getEid()), bxd.getBxlb()));
            bs.upbxdbyadmin(bxd);
        }

    }


    /**
     * @Description 调度器（Simple Triggers）
     * @param
     * @Return void
     * @Author wzh
     * @Date 2020/12/12 11:54
     */
    private void mySchedule() {
        try {
            //创建scheduler（调度器）实例
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            //创建JobDetail实例，创建要执行的job
            JobDetail jobDetail = JobBuilder.newJob(OrderListener.class)
                    .withIdentity("监听接单人超工时的工单", "group1").build();

            //构建Trigger（触发器）实例,每隔5s执行一次
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "监听接单人超工时的工单")
                    //立即生效
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            //每隔30分钟执行一次
                            .withIntervalInMinutes(30)
                            //一直执行
                            .repeatForever())
                    .build();

            //调度执行任务
            scheduler.scheduleJob(jobDetail, trigger);
            //启动
            scheduler.start();

            //睡眠
            //Thread.sleep(6000);

            //停止
            //scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Component
    public static class ApplicationRunnerImpl implements ApplicationRunner {

        @Override
        public void run(ApplicationArguments args) throws Exception {
            new OrderListener().mySchedule();
        }
    }
}
