package com.glyxybxhtxt.util;

import com.glyxybxhtxt.dataObject.Bxd;
import com.glyxybxhtxt.dataObject.Shy;
import com.glyxybxhtxt.service.*;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

/**
 * Author:wangzh
 * Date: 2021/1/7 17:38
 * Version: 1.0
 * description: 这个定时任务用来检查昨天下午5.30---今天早上9.00的订单的审核员状态
 * （也就是夜班的情况，因为只有一个审核员上班）
 */
public class ShyCheck implements Job {

    private BxdService bs = (BxdService) SpringContextUtil.getBean("bxdServiceImpl");

    private EwmService es = (EwmService) SpringContextUtil.getBean("ewmServiceImpl");

    private JdrService js = (JdrService) SpringContextUtil.getBean("jdrServiceImpl");

    private ShyService ss = (ShyService) SpringContextUtil.getBean("shyServiceImpl");

    private AutoOrder zdpd = (AutoOrder) SpringContextUtil.getBean("autoOrder");

    private MsgPushService ybmsg = (MsgPushService) SpringContextUtil.getBean("msgPushServiceImpl");

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //查询昨天的，下班时间申报的并且只有一个审核员审核的订单，也就是休息时间申报的报修单
        List<Bxd> xxsjbxd = bs.xxsjBxd();
        xxsjbxd.forEach(bxd -> {
            //查询一下今天已经在这个订单区域签到了的审核员
            List<Shy> alreadyQdShy = ss.selOptimalShy(bxd.getEid());
            int alreadyQdShyNum = alreadyQdShy.size();
            if(alreadyQdShyNum > 0) {
                Random sjfpshy = new Random();
                Bxd t = new Bxd();
                //随机分配审核员
                String chosenShy = alreadyQdShy.get(sjfpshy.nextInt(alreadyQdShyNum)).getYbid();
                t.setShy2(chosenShy);
                t.setId(bxd.getId());
                //修改审核员2
                bs.upbxdbyadmin(t);
                //易班推送
                ybmsg.msgpush(chosenShy,es.selxxwz(bxd.getEid())+"的报修单较紧急，请您及时到现场审核处理！");
                ybmsg.msgpush(bxd.getShy2(), "您需要去" + es.selxxwz(bxd.getEid()) + "审核的订单已经分配给了其他审核员，请注意！");
            }
        });
    }

    /**
     * @Description 调度器（Simple Triggers）
     * @param
     * @Return void
     * @Author wzh
     * @Date 2020/1/7 20:54
     */
    void mySchedule() {
        try {
            //创建scheduler（调度器）实例
//            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            SchedulerFactory factory = new StdSchedulerFactory();
            Scheduler scheduler = factory.getScheduler();

            //创建JobDetail实例，创建要执行的job
            JobDetail jobDetail = JobBuilder.newJob(OrderListener.class)
                    .withIdentity("监听下班时间订单的审核员2重新分配", "group2")
                    .build();

            //构建CronTrigger（触发器）实例,早上8点-早上10点每隔15分钟执行一次
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger2", "监听接单人超工时的工单重新分配")
//                    .startAt(DateBuilder.evenMinuteDate(new Date()))
                    .startNow()
//                    .endAt(new Date(System.currentTimeMillis() + 60 * 1000))
//                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
//                            //每隔30分钟执行一次
//                            .withIntervalInMinutes(30)
//                            //一直执行
//                            .repeatForever())
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/15 8-10 * * ?"))
                    .forJob(jobDetail)
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
            new ShyCheck().mySchedule();
        }
    }
}
