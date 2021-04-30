package com.glyxybxhtxt.util.quartzUtils;

import com.glyxybxhtxt.constant.GlmcConstants;
import com.glyxybxhtxt.dataObject.Bxd;
import com.glyxybxhtxt.dataObject.Jdr;
import com.glyxybxhtxt.service.impl.*;
import com.glyxybxhtxt.util.AutoOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:wangzh
 * Date: 2020/12/12 11:54
 * Version: 1.0
 * Description : 此类用来监听订单派单给总工时已经超过12的接单人，将这些单重新派给其他接单人
 */
@Slf4j
@Component
public class OrderListener {

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
    @Autowired
    private AutoOrder zdpd;


    public void executeGsCheck() {
        log.warn("监听工时超时工人中。。。。。");
        //查找工时超过12的接单人的订单
        //1、查找状态为1，2,3的接单人
        List<Jdr> allJdr1and2 = new ArrayList<>();
        allJdr1and2.addAll(js.selalljdr("1"));
        allJdr1and2.addAll(js.selalljdr("2"));
        allJdr1and2.addAll(js.selalljdr("3"));
        //今天工时>=12的接单人
        List<Jdr> gsgt2 = new ArrayList<>();
        //当前接单人工时超过12的且状态为已派单（state=1）的工单
        List<Bxd> state1gd = new ArrayList<>();

        //查找今天总工时已经>=12的接单人
        for (Jdr jdr : allJdr1and2) {
            Double gs = bs.selgs(jdr.getYbid());
            gs = ObjectUtils.isEmpty(gs) ? 0 : gs;
            if (gs >= GlmcConstants.GS) {
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
            ybmsg.msgpush(bxd.getJid(), "由于您今日工时已达标，您在" + es.selxxwz(bxd.getEid()) + "的报修单已经分配给其他接单人，请注意！");
            bxd.setJid(zdpd.zdpd(String.valueOf(bxd.getEid()), bxd.getBxlb()));
            bs.upbxdbyadmin(bxd);
        }
        log.warn("OrderListener结束。。。。。");
    }


    public String getCron() {
        //构建CronTrigger（触发器）实例,每天九点-十八点执行每隔20分钟执行一次
//         .withSchedule(CronScheduleBuilder.cronSchedule("0 0/20 9-18 * * ?"))
//         .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * 9-18 * * ?"))
        return "0/5 * 9-18 * * ?";
    }
}
