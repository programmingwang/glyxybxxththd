package com.glyxybxhtxt.util.quartzUtils;

import com.glyxybxhtxt.dataObject.Bxd;
import com.glyxybxhtxt.dataObject.Shy;
import com.glyxybxhtxt.service.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j
@Component
public class ShyCheck{
    @Autowired
    private BxdServiceImpl bs;
    @Autowired
    private EwmServiceImpl es;
    @Autowired
    private JdrServiceImpl js;
    @Autowired
    private ShyServiceImpl ss;
    @Autowired
    private MsgPushServiceImpl ybmsg;

    public void executeShyCheck() {
        log.info("监听审核员分配中。。。。。。");
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
        log.info("ShyCheck结束。。。。。");
    }


    public String getCron() {
        //构建CronTrigger（触发器）实例,早上8点-早上10点每隔15分钟执行一次
//                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/15 8-10 * * ?"))
//                        .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * 8-22 * * ?"))
        return "0/5 * 8-22 * * ?";
    }
}
