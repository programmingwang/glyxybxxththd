package com.glyxybxhtxt.controller.shy;

import com.glyxybxhtxt.dataObject.Bxd;
import com.glyxybxhtxt.dataObject.Qdb;
import com.glyxybxhtxt.response.ResponseData;
import com.glyxybxhtxt.service.BxdService;
import com.glyxybxhtxt.service.EwmService;
import com.glyxybxhtxt.service.MsgPushService;
import com.glyxybxhtxt.service.QdbService;
import com.glyxybxhtxt.util.ParseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:wangzh
 * Date: 2020/12/4 16:19
 * Version: 1.0
 */
@RestController
public class ShyServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    private BxdService bs;
    @Autowired
    private QdbService qs;
    @Autowired
    private ParseUtil parse;
    @Autowired
    private MsgPushService ybmsg;
    @Autowired
    private EwmService es;

    @RequestMapping("/ShyServlet")
    @ResponseBody
    ResponseData shyServlet(@RequestParam("op")String op, @RequestParam(value = "bid", required = false)String bid,
                            @RequestParam(value = "hc", required = false)String hc, @RequestParam(value = "gs", required = false)String gs,
                            @RequestParam(value = "ybid", required = false)String ybid, @RequestParam(value = "num", required = false)String num,
                            @RequestParam(value = "page", required = false)String page, @RequestParam(value = "xq", required = false)String xq,
                            @RequestParam(value = "state", required = false)String state, @RequestParam(value = "shyid", required = false)String shyid,
                            @RequestParam(value = "shystate", required = false)String shystate, @RequestParam(value = "eid", required = false)String eid) {
        if(StringUtils.isWhitespace(op) || StringUtils.isEmpty(op) || StringUtils.isBlank(op))
            return new ResponseData("2");
        switch (op){
            case "selbxdbyshy" : return selbxdbyshy(shyid, eid, shystate);
            case "upbxdbyshy" : return upbxdbyshy(shyid, bid, shystate);
            case "qd" : return qd(ybid, xq, state);
            case "selqdb" : return selqdb(num, page, ybid);
            case "upbxdhcbyshy" : return upbxdhcbyshy(bid,hc,gs,ybid);
            case "upbxdbyysr" : return upbxdbyysr(shyid,bid,state);
            default: return new ResponseData(false);
        }
    }

    /**
     * 当审核员1或者审核员2处理报修单，则根据该报修单id，来进行更新处理结果
     * 其他更新条件：
     * 1.如果耗材了，则在报修单表加上耗材内容
     * 2.如果接单人消耗工时了，则在报修单表加上消耗的工时
     */
    private ResponseData upbxdhcbyshy(String bid, String hc, String gs, String ybid) {
        Bxd b = new Bxd();
        if(bid==null||ybid==null){
            return new ResponseData("3");
        }
        b.setId(Integer.parseInt(bid));
        b.setShy1(ybid);
        if(hc!=null)
            b.setHc(hc);
        if(gs!=null)
            b.setGs(gs);
        return bs.upbxdhcbyshy(b) ==1
                ? new ResponseData(true)
                : new ResponseData("该审核员无法修改此报修单") ;
    }

    /**
     * 根据审核员id，查询该审核员的所有签到表
     */
    private ResponseData selqdb(String num, String page, String ybid) {
        Qdb q = new Qdb();
        int num2=30;
        if(num!=null){
            num2 = Integer.parseInt(num);
        }
        q.setId(num2);

        if(page==null){
            q.setState(0);
        }else{
            int page2 = (Integer.parseInt(page)-1)*num2;
            q.setState(page2);
        }

        q.setShyid(ybid);
        List<Qdb> qlist = qs.selallqy(q);
        Map<String,Object> map = new HashMap<>();
        map.put("qlist", qlist);
        return new ResponseData(map);
    }

    /**
     * 插入一条审核员的签到数据到签到表
     */
    private ResponseData qd(String ybid, String xq, String state) {
        if("0".equals(xq))
        {
            xq="临桂校区";
        }else if("1".equals(xq)){
            xq="东城校区";
        }
        Qdb q = new Qdb();
        q.setShyid(ybid);
        q.setXq(xq);
        q.setState(Integer.parseInt(state));
        return qs.qd(q) ? new ResponseData(true) : new ResponseData("db error") ;
    }

    /**
     * 当审核员1或者审核员2处理报修单，则根据该报修单id，来进行更新处理结果
     * 并查询该报修单是否已经真正处理，然后推送结果给接单人
     */
    private ResponseData upbxdbyshy(String shyid, String bid, String shystate) {
        if(shyid==null||bid==null||shystate==null){
            return new ResponseData("3");
        }
        int id = Integer.parseInt(bid);
        int state = Integer.parseInt(shystate);
        Bxd t = bs.selbxdforshyid(id);
        Bxd b = new Bxd();
        b.setId(id);
        if(StringUtils.equals(shystate,"2")){
            b.setHc("");
            b.setGs("");
            ybmsg.msgpush(t.getJid(),"您填写的耗材和工时未通过审核，请及时修改！是在"+es.selxxwz(t.getEid())+"的报修单");
        }
        if(shyid.equals(t.getShy1())){
            if (StringUtils.equals(shystate, "2")) {
                b.setShy1state(state);
                b.setShy2state(state);
            } else {
                b.setShy1state(state);
            }
            bs.upbxd1byshy(b);
        }else if(shyid.equals(t.getShy2())){
            if (StringUtils.equals(shystate, "2")) {
                b.setShy1state(state);
                b.setShy2state(state);
            } else {
                b.setShy2state(state);
            }
            bs.upbxd2byshy(b);
        }else{
            return new ResponseData("审核员id无效");
        }
        //这里应该再查询一次数据库报修单（有问题）
        Bxd checkShyState = bs.selbxdforshyid(id);
        if(checkShyState.getShy1state() == 1 && checkShyState.getShy2state() == 1){
            ybmsg.msgpush(checkShyState.getJid(),"审核员审核通过，请尽快前往维修！详细地点："+es.selxxwz(checkShyState.getEid()));
        }
        return new ResponseData(true);
    }

    /**
     * 更新报修单的状态，并通知处理该报修单的接单人
     */
    private ResponseData upbxdbyysr(String shyid, String bid, String tstate) {
        if(shyid==null||bid==null||tstate==null){
            return new ResponseData("3");
        }
        int id = Integer.parseInt(bid);
        int state = Integer.parseInt(tstate);
        if (state != 4){
            if (state != 5){
                return new ResponseData("3");
            }
        }
        Bxd t = bs.selbxdforshyid(id);
        Bxd b = new Bxd();
        b.setId(id);
        if(shyid.equals(t.getShy1())){
            b.setState(state);
            if (state == 4) {
                ybmsg.msgpush(t.getJid(), "您在" + es.selxxwz(t.getEid()) + "维修的订单验收通过了！");
                b.setYssj(new Date());
//                    : ybmsg.msgpush(t.getJid(),"您有订单验收不通过，请及时处理！链接：http://yiban.glmc.edu.cn/bx?eid="+t.getEid()+"\n报修单地点："+es.selxxwz(t.getEid()));
            }else {
                ybmsg.msgpush(t.getJid(),"您有订单验收不通过，请及时处理！报修单地点："+es.selxxwz(t.getEid()));
            }
            bs.upbxdbyysr(b);
        }else{
            return new ResponseData("验收员id无效");
        }
        return new ResponseData(true);
    }

    /**
     * 根据审核员id，获得该审核员  处理  过的报修单。报修单不包括已撤回的
     * 处理 = 审核通过 | 审核不通过
     * 如果二维码id不为空，获得的报修单是该二维码区域的
     */
    private ResponseData selbxdbyshy(String shyid, String eid, String shystate) {
        String ybid = shyid;
        if(ybid==null){
            return new ResponseData("3");
        }
        Bxd b = new Bxd();
        String state = shystate;
        if(state!=null)b.setShy1state(Integer.parseInt(state));
        if(eid!=null)b.setEid(Integer.parseInt(eid));
        b.setShy1(ybid);
        List<Bxd> blist = bs.selbxdbyshy(b);
        for (Bxd bxd : blist) {
            bxd.setBxlb(parse.paraseBxlb(bxd.getBxlb()));
            bxd.setHc(parse.paraseHc(bxd.getHc()));
        }
        Map<String,Object> map = new HashMap<>();
        map.put("blist", blist);
        return new ResponseData(map);
    }
}
