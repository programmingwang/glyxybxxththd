package com.glyxybxhtxt.util;

import com.glyxybxhtxt.constant.GlmcConstants;
import com.glyxybxhtxt.dataObject.Jdr;
import com.glyxybxhtxt.service.*;
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
    @Resource
    private MsgPushService ybmsg;
    
    public String zdpd(String eid, String bxlb){
        /**
         * 查找符合接单条件的接单人直接派单
         * 1、筛选state=1的
         * 2、工时<12的
         * 3、查到符合条件(业务范围符合，在bxd区域内的)的接单人的ybid,jdrybid
         * 4、bxd.setJid(jdrybid)
         */
            //获得当前的修改地点的二维码，实质上是为了查询到二维码的校区
            //当前二维码所属的校区
            //获得所有状态为1(state只是第三方工作人员的在职状态，并不是是否在线，是否在线要查签到表，
            //查看最近一次签到是否为‘签到’（‘签到’就派单)的接单人,当然state=1也需要），
            //还有就是签到地点得与当前二维码所在校区一样
            
            //selOptimaljdr：获取在接单人状态为1，签到状态为1，符合报修列表的接单人信息。其中前者1为在职。后者1为已签到
            List<Jdr> canjd = js.selOptimaljdr(bxlb,Integer.parseInt(eid));
            //工时满足要求的接单人，只有这些接单人才能被自动派单
            List<Jdr> gscanjd = new ArrayList<>();
            for (Jdr jdr : canjd) {
                //接单人总工时小于12 并且 接单人的业务范围包括报修类别 并且 bxd的xq和最近一次签到在一个xq的（！！！业务范围改成具体能干啥了！！！）
                Double gs = bs.selgs(jdr.getYbid());
                gs = ObjectUtils.isEmpty(gs) ? 0 : gs ;
                // GlmcConstants.GS：12.0
                if( gs < GlmcConstants.GS){
                    gscanjd.add(jdr);
                }
            }

            if(gscanjd.size() == 0){
//                return "6U@U6WX2^&nb6YIILV当前没有可以接单的接单人了！请尽快电话联系后勤处为您安排接单人！";
                return "wjdr";
            }
            //gscanjd里就是符合要求的接单人  可以理解成热点数据，
            //随机派单
            Random random = new Random();
            //随机出来的接单人，可以进行派单了
            Jdr jdr = gscanjd.get(random.nextInt(gscanjd.size()));
            ybmsg.msgpush(jdr.getYbid(),"您有新的维修订单了，请及时处理！详细地点："+es.selxxwz(Integer.parseInt(eid)));
            return jdr.getYbid();
        }
    }
