package com.glyxybxhtxt.util;

import com.glyxybxhtxt.dataObject.Ewm;
import com.glyxybxhtxt.dataObject.Jdr;
import com.glyxybxhtxt.service.BxdService;
import com.glyxybxhtxt.service.EwmService;
import com.glyxybxhtxt.service.JdrService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Author:wangzh
 * Date: 2020/12/12 12:15
 * Version: 1.0
 */
@Component
public class AutoOrder {
    @Resource
    private BxdService bs;
    @Resource
    private EwmService es;
    @Resource
    private JdrService js;

    public String zdpd(String eid){
        /**
         * 查找符合接单条件的接单人直接派单
         * 1、筛选state=1的
         * 2、工时<2的
         * 3、查到符合条件(业务范围符合，在bxd区域内的)的接单人的ybid,jdrybid
         * 4、bxd.setJid(jdrybid)
         */
        //获得当前的修改地点的二维码，实质上是为了查询到二维码地点
        Ewm ewm = es.selqybysbr(Integer.parseInt(eid));
        //获得所有状态为1的接单人
        List<Jdr> canjd = js.selalljdr("1");
        //工时满足要求的接单人，只有这些接单人才能被自动派单
        List<Jdr> gscanjd = new ArrayList<>();
        for (Jdr jdr : canjd) {
            //接单人总工时小于2 并且 接单人的业务范围属于这个二维码的区域
            Double gs = bs.selgs(jdr.getYbid());
            gs = ObjectUtils.isEmpty(gs) ? 0 : gs ;
            if( gs < Double.parseDouble("2") && StringUtils.contains(jdr.getYwfw(),String.valueOf(ewm.getQid()))){
                gscanjd.add(jdr);
            }
        }
        //gscanjd里就是符合要求的接单人  可以理解成热点数据，但是这个访问量不大，考虑是否引入guava生态
        //随机派单
        Random random = new Random();
        //随机出来的接单人，可以进行派单了
        Jdr jdr = gscanjd.get(random.nextInt(gscanjd.size()));
        return jdr.getYbid();
    }
}
