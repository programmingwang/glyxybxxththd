package com.glyxybxhtxt.controller.admin;

import com.glyxybxhtxt.dataObject.*;
import com.glyxybxhtxt.response.ResponseData;
import com.glyxybxhtxt.service.*;
import com.glyxybxhtxt.util.ParseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author:wangzh
 * Date: 2020/12/4 17:29
 * Version: 1.0
 */
@RestController
@SuppressWarnings("all")
public class AdminServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    private BxdService bs;
    @Autowired
    private BxqyService qs;
    @Autowired
    private JdrService js;
    @Autowired
    private ShyService ss;
    @Autowired
    private QdbService qdbs;
    @Autowired
    private EwmService es;
    @Autowired
    private ParseUtil parse;
    @Autowired
    private MsgPushService ybmsg;


    @RequestMapping("/AdminServlet")
    @ResponseBody
    ResponseData adminServlet(@RequestParam("op")String op, @RequestParam(value = "shyid",required = false) String shyid,
                              @RequestParam(value = "num",required = false) String num, @RequestParam(value = "state",required = false)String state,
                              @RequestParam(value = "qid",required = false) String qid, @RequestParam(value = "xxdd",required = false) String xxdd,
                              @RequestParam(value = "qy",required = false) String qy, @RequestParam(value = "qylb",required = false) String qylb,
                              @RequestParam(value = "xq",required = false) String xq, @RequestParam(value = "x",required = false) String x,
                              @RequestParam(value = "y",required = false) String y, @RequestParam(value = "eid",required = false) String eid,
                              @RequestParam(value = "jid",required = false) String jid, @RequestParam(value = "xm",required = false) String xm,
                              @RequestParam(value = "del",required = false) String del, @RequestParam(value = "zw",required = false) String zw,
                              @RequestParam(value = "sj",required = false) String sj, @RequestParam(value = "yx",required = false) String yx,
                              @RequestParam(value = "ywfw",required = false) String ywfw, @RequestParam(value = "ybid",required = false) String ybid,
                              @RequestParam(value = "gh",required = false) String gh, @RequestParam(value = "bid",required = false) String bid,
                              @RequestParam(value = "shy1",required = false) String shy1, @RequestParam(value = "shy2",required = false) String shy2,
                              @RequestParam(value = "pj",required = false) String pj, @RequestParam(value = "startime",required = false) String startime,
                              @RequestParam(value = "endtime",required = false) String endtime, @RequestParam(value = "pjnr",required = false) String pjnr,
                              @RequestParam(value = "hc",required = false) String hc, @RequestParam(value = "gs",required = false) String gs,
                              @RequestParam(value = "bxlb",required = false) String bxlb) throws  ParseException {
        Map<String,Object> shymap = new HashMap<>();
        shymap.put("slist",ss.selallqy());
        if(StringUtils.isWhitespace(op) || StringUtils.isEmpty(op) || StringUtils.isBlank(op))
            return new ResponseData("2");
        switch (op){
            case "selbxdbyadmin" : return selbxdbyadmin(bid, startime, endtime, xq, qid, jid, state, pj);
            case "selallqy" : return selallqy(xq);
            case "selalljdr" : return selalljdr(state);
            case "selallshy" : return new ResponseData(shymap);
            case "selqdb" : return selqdb(shyid, num);
            case "upbxdbyadmin" : return upbxdbyadmin(del, bid, jid, shy1, shy2, pj, pjnr, hc, gs);
            case "newpeople" : return newpeople(ywfw, ybid, gh, xm, zw, sj, yx);
            case "uppeople" : return uppeople(jid, xm, shyid, del, zw, sj, yx, ywfw, state);
            case "selewm" : return selewm(qid);
            case "newqy" : return newqy(qy, qylb, xq, x, y);
            case "newewm" : return newewm(qid,xxdd);
            case "upqy" : return upqy(qid, qy, qylb, xq, x, y);
            case "upewm" : return upewm(eid, qid, xxdd);
            case "bxnum" : return bxnum(state);
            case "adminindex" : return adminindex();
            case "selOptimaljdrPC" : return selOptimaljdrPC(bid);
            case "selbxdbyadminpc" : return selbxdbyadminpc(bid, startime, endtime, xq, qid, jid, state, pj);
            default: return new ResponseData(false);
        }
    }

    private ResponseData adminindex() {
        Map<String,Object> map = new HashMap<>();
        int zbxd = bs.allcount();
        //验收单+完成单才是完成的订单
        int zwxd = bs.selnumforstate(2) + bs.selnumforstate(4);
        //正在维修的单
        int zzwx = bs.selnumforstate(1) + bs.selnumforstate(5);
        //撤销的单
        int zcxd = bs.selnumforstate(3);
        map.put("tj", bs.tj());
        map.put("zbxd", zbxd);
        map.put("zwxd", zwxd);
        map.put("zcxd", zcxd);
        map.put("zzwx", zzwx);
        map.put("pj1", bs.selnumforpj(1));
        map.put("pj2", bs.selnumforpj(2));
        map.put("pj3", bs.selnumforpj(3));
        map.put("pj4", bs.selnumforpj(4));
        map.put("pj5", bs.selnumforpj(5));
        return new ResponseData(map);
    }

    private ResponseData bxnum(String state) {
        Map<String,Object> map = new HashMap<>();
        int count = bs.selnumforstate(Integer.parseInt(state));
        map.put("status", "success");
        map.put("count", count);
        return new ResponseData(map);
    }

    private ResponseData newewm(String qid, String xxdd) {
        Ewm e = new Ewm();
        e.setQid(Integer.parseInt(qid));
        e.setXxdd(xxdd);
        return es.newewm(e) ? new ResponseData(true) : new ResponseData(false);
    }

    private ResponseData newqy(String qy, String qylb, String xq, String x, String y) {
        if("0".equals(xq))
        {
            xq="临桂校区";
        }else if("1".equals(xq)){
            xq="东城校区";
        }
        Bxqy q = new Bxqy();
        q.setQy(qy);
        q.setQylb(qylb);
        q.setXq(xq);
        q.setX(x);
        q.setY(y);
        return qs.newqy(q) ? new ResponseData(true) : new ResponseData(false);
    }

    private ResponseData upewm(String eid, String qid, String xxdd) {
        Ewm e = new Ewm();
        e.setId(Integer.parseInt(eid));
        e.setQid(Integer.parseInt(qid));
        e.setXxdd(xxdd);
        return es.upewm(e)? new ResponseData(true) : new ResponseData(false);
    }

    private ResponseData upqy(String qid, String qy, String qylb, String xq, String x, String y) {
        if("0".equals(xq))
        {
            xq="临桂校区";
        }else if("1".equals(xq)){
            xq="东城校区";
        }
        Bxqy q = new Bxqy();
        q.setId(Integer.parseInt(qid));
        q.setQy(qy);
        q.setQylb(qylb);
        q.setXq(xq);
        q.setX(x);
        q.setY(y);
        return qs.upqy(q)? new ResponseData(true) : new ResponseData(false);
    }

    private ResponseData selewm(String qid) {
        Map<String, Object> map = new HashMap<>();
        map.put("ewmlist", es.selewm(Integer.parseInt(qid)));
        return new ResponseData(map);
    }

    private ResponseData uppeople(String jid, String xm, String shyid, String del, String zw, String sj, String yx, String ywfw, String state) {
        ResponseData responseData = null;
        if(jid==null){
            if(shyid==null){
                return new ResponseData("3");
            }
            if("1".equals(del)){
                ss.del(shyid);
                return new ResponseData("success","审核员删除成功");
            }
            Shy s = new Shy();
            s.setYbid(shyid);
            s.setXm(xm);
            if(zw!=null)s.setZw(Integer.parseInt(zw));
            ss.UPshy(s);
            responseData = new ResponseData("success","审核员修改成功");
        }else{
            if("1".equals(del)){
                js.del(jid);
                return new ResponseData("success","接单人删除成功");
            }
            Jdr j = new Jdr();
            j.setXm(xm);
            j.setYbid(jid);
            j.setSj(sj);
            j.setYx(yx);
            j.setState(state);
            j.setYwfw(ywfw);
            js.upjdr(j);
            responseData = new ResponseData("success","接单人修改成功");
        }
        return responseData;
    }

    private ResponseData newpeople(String y, String ybid, String gh, String xm, String zw, String sj, String yx) {
        ResponseData responseData = null;
        if(ybid==null||gh==null||xm==null){
            return new ResponseData("3");
        }
        if(y==null){
            Shy s = new Shy();
            if(zw==null){
                return new ResponseData("3");
            }
            s.setZw(Integer.parseInt(zw));
            s.setGh(gh);
            s.setXm(xm);
            s.setYbid(ybid);
            ss.newshy(s);
            responseData = new ResponseData("success","审核员添加成功");
        }else{
            Jdr j = new Jdr();
            j.setGh(gh);
            j.setXm(xm);
            j.setYbid(ybid);
            j.setSj(sj);
            j.setYx(yx);
            j.setYwfw(y);
            js.newjdr(j);
            responseData = new ResponseData("success","接单人添加成功");
        }
        return responseData;
    }

    private ResponseData upbxdbyadmin(String del, String bid, String jid, String shy1, String shy2, String pj, String pjnr, String hc, String gs) {
        ResponseData responseData = null;
        Bxd b = new Bxd();
        if(bid==null){
            return new ResponseData("3");
        }
        Integer id = Integer.parseInt(bid);
        //查询当前的报修单
        Bxd currentBxd = bs.selonebxd(id);
        b.setId(id);
        if("1".equals(del)){
            bs.del(id);
            responseData =  new ResponseData("success","删除成功");
        }else{
            String bxdxxdd = es.selxxwz(currentBxd.getEid());
            b.setJid(jid);
            if(!StringUtils.equals(jid,currentBxd.getJid())){
                ybmsg.msgpush(jid,
                        "管理员为您分配新的维修订单了，请及时处理！详细地点："+ bxdxxdd);
                b.setState(1);
                b.setJdsj(new Date());
            }

            b.setShy1(shy1);
            if(!StringUtils.equals(shy1,currentBxd.getShy1()) && !StringUtils.equals(shy1,currentBxd.getShy2())) ybmsg.msgpush(shy1,
                    "管理员为您分配新的订单审核了，请及时处理！详细地点："+ bxdxxdd);

            b.setShy2(shy2);
            if(!StringUtils.equals(shy2,currentBxd.getShy2()) && !StringUtils.equals(shy2,currentBxd.getShy1())) ybmsg.msgpush(shy2,
                    "管理员为您分配新的订单审核了，请及时处理！详细地点："+ bxdxxdd);

            b.setPj(pj);
            b.setPjnr(pjnr);
//            b.setHc(hc);
            b.setGs(gs);
            bs.upbxdbyadmin(b);
            responseData =  new ResponseData("success","修改成功");
        }
        return responseData;
    }

    private ResponseData selqdb(String shyid,String num) {
        if(shyid==null){
            return new ResponseData("3");
        }
        Qdb q = new Qdb();
        if(num!=null){
            q.setId(Integer.parseInt(num));
        }
        q.setShyid(shyid);
        Map<String, Object> map = new HashMap<>();
        map.put("qdblist", qdbs.selallqy(q));
        return new ResponseData(map);
    }

    private ResponseData selalljdr(String state) {
        Map<String,Object> map = new HashMap<>();
        List<String> states = null;
        if(state == null){
            map.put("jlist", js.selalljdr(null));
        }else {
            map.put("jlist", js.selalljdr(Arrays.asList(state)));
        }
        return new ResponseData(map);
    }

    private ResponseData selOptimaljdrPC(String bid) {
        Integer bxdid = Integer.parseInt(bid);
        Bxd selonebxd = bs.selonebxd(bxdid);
        String bxlb = selonebxd.getBxlb();
        Integer eid = selonebxd.getEid();
        Map<String,Object> map = new HashMap<>();
        //查询适合的接单人
        map.put("jlist", js.selOptimaljdrPC(bxlb, eid));
        return new ResponseData(map);
    }

    private ResponseData selallqy(String xq) {
        if(xq==null){
            Map<String,Object> map = new HashMap<>();
            map.put("qylist", qs.selallqy());
            return new ResponseData(map);
        }
        else{
            Map<String,Object> map = new HashMap<>();
            if("0".equals(xq))
            {
                xq="临桂校区";
            }else if("1".equals(xq)){
                xq="东城校区";
            }
            List<Bxqy> qylist = qs.ditu(xq);
            for(int i=0;i<qylist.size();i++){
                List<Bxd> b = qylist.get(i).getB();
                if(b==null){
                    qylist.get(i).setCountb(0);
                }else{
                    qylist.get(i).setCountb(b.size());
                }
            }
            map.put("qylist", qylist);
            return new ResponseData(map);
        }
    }

    private ResponseData selbxdbyadmin(String bid, String startime, String endtime, String xq, String qy, String jdr, String state, String pj) throws ParseException {
        Bxd b = new Bxd();
        if(bid!=null)b.setId(Integer.parseInt(bid));
        Date star = null;
        Date end = null;
        if(startime!=null) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            star = format.parse(startime);
            Calendar cal = new GregorianCalendar();
            cal.setTime(star);
            star = cal.getTime();
        }
        if(endtime!=null) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            end = format.parse(endtime);
            Calendar cal = new GregorianCalendar();
            cal.setTime(end);
            cal.add(cal.DATE, 1);
            end = cal.getTime();
        }
        b.setSbsj(star);
        b.setWxsj(end);
        b.setBxlb(xq);
        b.setBxnr(qy);
        b.setJid(jdr);
        if(state!=null)b.setState(Integer.parseInt(state));
        b.setPj(pj);
        List<Bxd> blist = bs.selbxdbyadmin(b);
        for (Bxd bxd : blist) {
            bxd.setBxlb(parse.paraseBxlb(bxd.getBxlb()));
            bxd.setS1(ss.selOneShy(bxd.getShy1()));
            bxd.setS2(ss.selOneShy(bxd.getShy2()));
        }
        Map<String,Object> map = new HashMap<>();
        map.put("blist", blist);
        return new ResponseData(map);
    }

    private ResponseData selbxdbyadminpc(String bid, String startime, String endtime, String xq, String qy, String jdr, String state, String pj) throws ParseException {
        Bxd b = new Bxd();
        if(bid!=null)b.setId(Integer.parseInt(bid));
        Date star = null;
        Date end = null;
        if(startime!=null) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            star = format.parse(startime);
            Calendar cal = new GregorianCalendar();
            cal.setTime(star);
            star = cal.getTime();
        }
        if(endtime!=null) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            end = format.parse(endtime);
            Calendar cal = new GregorianCalendar();
            cal.setTime(end);
            cal.add(cal.DATE, 1);
            end = cal.getTime();
        }
        b.setSbsj(star);
        b.setWxsj(end);
        b.setBxlb(xq);
        b.setBxnr(qy);
        b.setJid(jdr);
        if(state!=null)b.setState(Integer.parseInt(state));
        b.setPj(pj);
        List<Bxd> blist = bs.selbxdbyadmin(b);
        for (Bxd bxd : blist) {
            bxd.setBxlb(parse.paraseBxlb(bxd.getBxlb()));
            bxd.setHc(parse.paraseHc1(bxd.getHc()));
            bxd.setS1(ss.selOneShy(bxd.getShy1()));
            bxd.setS2(ss.selOneShy(bxd.getShy2()));
        }
        Map<String,Object> map = new HashMap<>();
        map.put("blist", blist);
        return new ResponseData(map);
    }
}
