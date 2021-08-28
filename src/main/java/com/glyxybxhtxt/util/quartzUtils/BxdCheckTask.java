package com.glyxybxhtxt.util.quartzUtils;


import com.glyxybxhtxt.dataObject.Bxd;
import com.glyxybxhtxt.response.ResponseData;
import com.glyxybxhtxt.service.EwmService;
import com.glyxybxhtxt.service.impl.BxdServiceImpl;
import com.glyxybxhtxt.service.impl.EwmServiceImpl;
import com.glyxybxhtxt.service.impl.MsgPushServiceImpl;
import com.glyxybxhtxt.util.AutoOrder;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author wzh
 * @Date 2021/5/21 1:03 下午
 * @Description: 未派单报修单定时任务轮询
 */
@Slf4j
@Component
public class BxdCheckTask {
    @Autowired
    private BxdServiceImpl bs;
    @Autowired
    private EwmServiceImpl es;
    @Autowired
    private MsgPushServiceImpl ybmsg;
    @Autowired
    private AutoOrder zdpd;

    public void executeBxdCheck(){
        log.info("监听未派单工单，并未这些工单重新派单");
        //20个没有派单的报修单
        List<Bxd> wpdBxds = bs.selwpdbxd();
        wpdBxds.forEach(wpdbxd ->{
            //自动派单根据条件筛选的符合条件的一个接单人
            String jid = this.zdpd.zdpd(String.valueOf(wpdbxd.getEid()), wpdbxd.getBxlb());
            if(StringUtils.startsWith(jid, "wjdr")){
                //wjdr = 无接单人
                return;
            }
            Bxd bxd = new Bxd();
            bxd.setJid(jid);
            bxd.setState(1);
            bxd.setJdsj(new Date());
            bxd.setId(wpdbxd.getId());
            bs.upbxdbyadmin(bxd);
            ybmsg.msgpush(jid,"您有新的维修订单了，请及时处理！详细地点："+es.selxxwz(wpdbxd.getEid()));
        });
        log.info("未派单工单轮询结束");
    }

}
