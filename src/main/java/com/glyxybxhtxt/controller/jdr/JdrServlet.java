package com.glyxybxhtxt.controller.jdr;

import com.glyxybxhtxt.dataObject.Bxd;
import com.glyxybxhtxt.response.ResponseData;
import com.glyxybxhtxt.service.BxdService;
import com.glyxybxhtxt.service.EwmService;
import com.glyxybxhtxt.service.MsgPushService;
import com.glyxybxhtxt.util.ParseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:wangzh
 * Date: 2020/12/4 16:01
 * Version: 1.0
 */
@RestController
public class JdrServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    private BxdService bs;
    @Autowired
    private EwmService es;
    @Autowired
    private ParseUtil parse;
    @Autowired
    private MsgPushService ybmsg;

    @RequestMapping("/JdrServlet")
    @ResponseBody
    ResponseData jdrServlet(@RequestParam("op") String op, @RequestParam(value = "jid", required = false) String jid,
                            @RequestParam(value = "bid", required = false) String bid, @RequestParam(value = "state", required = false) String state,
                            @RequestParam(value = "hc", required = false) String hc, @RequestParam(value = "gs", required = false) String gs,
                            @RequestParam(value = "eid", required = false) String eid, @RequestParam(value = "ybid", required = false) String ybid) {
        if (StringUtils.isWhitespace(op) || StringUtils.isEmpty(op) || StringUtils.isBlank(op))
            return new ResponseData("2");
        switch (op) {
            case "selbxdbyjdr":
                return selbxdbyjdr(jid, eid, state);
            case "upbxdbyjdr":
                return upbxdbyjdr(jid, bid, state, hc, gs);
            case "selgs":
                return selgs(ybid);
            default:
                return new ResponseData(false);
        }
    }
    
    /**
     * 查询接单人当天 已维修和已验收 报修单的所花费的工时
     * 存在工时则四舍五入计算，保留小数点 12 位，否则为0
     */
    @ResponseBody
    private ResponseData selgs(String jid) {
        Map<String, Object> map = new HashMap<>();
        Double gs = bs.selgs(jid);
        if (gs == null || gs == 0.0) {
            map.put("gs", 0);
        } else {
            map.put("gs", gs);
        }
        return new ResponseData(map);
    }

    /**
     * 通过接单人id和报修单id，来更新该报修单的数据，如耗材工时
     */
    @ResponseBody
    private ResponseData upbxdbyjdr(String jid, String bid, String state, String hc, String gs) {
        ResponseData responseData = null;
        if (jid == null || bid == null) {
            return new ResponseData("3");
        }
        //查看bxd是否有hc信息了，如果有耗材信息，证明当前就是一个返工订单
        String originHc = bs.selishc(Integer.parseInt(bid));
        hc = StringUtils.isBlank(originHc) ? hc : originHc + "|返工耗材:" + hc;
        Bxd b = new Bxd();
        b.setJid(jid);
        b.setId(Integer.parseInt(bid));
        if (state != null) {
            //查询当前维修单信息
            Bxd checkBxd = bs.selonebxd(Integer.parseInt(bid));
            b.setState(Integer.parseInt(state));
            bs.upbxdbyjdr(b);
            responseData = new ResponseData("success", "修改维修状态成功");
            ybmsg.msgpush(checkBxd.getShy1(), "维修师傅已完成维修，请您尽快到现场验收！详细地点：" + es.selxxwz(checkBxd.getEid()));
        } else {
            Bxd t = bs.selbxdforshyid(b.getId());
            if (t == null || t.getId() == null) {
                responseData = new ResponseData("bidfalse");
            }
            if ((2 == t.getShy1state() || 2 == t.getShy2state())) {
                b.setHc(hc);
                b.setGs(gs);
                b.setShy1state(0);
                b.setShy2state(0);
                bs.upbxdbyjdr(b);
                responseData = new ResponseData("success", "重填耗材、工时成功");
                ybmsg.msgpush(t.getShy1(), "接单人已重新填写耗材工时，请及时审核！链接：http://yiban.glmc.edu.cn/bx?eid=" + t.getEid() + "\n单号" + t.getId());
                ybmsg.msgpush(t.getShy2(), "接单人已重新填写耗材工时，请及时审核！链接：http://yiban.glmc.edu.cn/bx?eid=" + t.getEid() + "\n单号" + t.getId());
            } else if (!(0 == t.getShy1state() || 0 == t.getShy2state())) {
                responseData = new ResponseData("耗材及工时已审核，无法修改");
            } else {
                b.setHc(hc);
                b.setGs(gs);
                bs.upbxdbyjdr(b);
                responseData = new ResponseData("success", "设置耗材、工时成功");
//                ybmsg.msgpush(t.getShy1(),"您有报修单需要审核，请及时处理！链接：http://yiban.glmc.edu.cn/bx?eid="+t.getEid()+"\n单号"+t.getId());
                ybmsg.msgpush(t.getShy1(), "您有报修单需要审核，请及时处理！详细地点：" + es.selxxwz(t.getEid()));
//                ybmsg.msgpush(t.getShy2(),"您有报修单需要审核，请及时处理！链接：http://yiban.glmc.edu.cn/bx?eid="+t.getEid()+"\n单号"+t.getId());
                ybmsg.msgpush(t.getShy2(), "您有报修单需要审核，请及时处理！详细地点：" + es.selxxwz(t.getEid()));
            }
        }
        return responseData;
    }

    /**
     * 根据接单人的易班id，获取由该接单人处理的报修单
     * 如果报修单的状态不为空、二维码id不为空，也加入判断中
     */
    @ResponseBody
    private ResponseData selbxdbyjdr(String jid, String eid, String state) {
        Map<String, Object> map = new HashMap<>();
        if (jid == null) {
            return new ResponseData("3");
        }
        Bxd b = new Bxd();
        b.setJid(jid);
        if (eid != null) b.setEid(Integer.parseInt(eid));
        if (state != null) b.setState(Integer.parseInt(state));
        List<Bxd> blist = bs.selbxdbyjdr(b);
        for (Bxd bxd : blist) {
            bxd.setBxlb(parse.paraseBxlb(bxd.getBxlb()));
            bxd.setHc(parse.paraseHc(bxd.getHc()));
        }
        map.put("blist", blist);
        return new ResponseData(map);
    }

}
