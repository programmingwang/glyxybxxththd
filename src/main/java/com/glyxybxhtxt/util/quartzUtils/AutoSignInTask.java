package com.glyxybxhtxt.util.quartzUtils;

import com.glyxybxhtxt.dataObject.Jdr;
import com.glyxybxhtxt.dataObject.Qdb;
import com.glyxybxhtxt.dataObject.Shy;
import com.glyxybxhtxt.service.JdrService;
import com.glyxybxhtxt.service.QdbService;
import com.glyxybxhtxt.service.ShyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * @author Young Kbt
 * @date 2021/11/16 12:34
 * @description 自动签到
 */
@Component
@Slf4j
public class AutoSignInTask {
    @Autowired
    private QdbService qdbService;
    
    @Autowired
    private JdrService jdrService;

    /**
     * 每天早上六点，接单人自动签到
     */
    public void autoAck() {
        log.info("现在是早上六点，开始自动签到");
        Qdb q = new Qdb();
        List<Jdr> jdrList = jdrService.selalljdr(null);
        for (Jdr jdr : jdrList) {
            // 统一签到
            q.setShyid(jdr.getYbid());
            // 根据易班 id，获取签到表信息，主要获取上一个签到的校区
            List<Qdb> selallqy = qdbService.selallqy(q);
            if(selallqy == null || selallqy.size() == 0) {
                log.info("该接单人没有签到历史，请手动签到一次，用于记录");
                return;
            }
            // 签到信息很多，获取最新的，也就是第一个
            String xq = selallqy.get(0).getXq();
            q.setXq(xq);
            q.setState(1);
            qdbService.qd(q);
        }
        log.info("所有接单人自动签到成功");
    }
    
}
