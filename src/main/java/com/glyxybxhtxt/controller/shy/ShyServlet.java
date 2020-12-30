package com.glyxybxhtxt.controller.shy;

import com.glyxybxhtxt.dataObject.Bxd;
import com.glyxybxhtxt.dataObject.Qdb;
import com.glyxybxhtxt.response.ResponseData;
import com.glyxybxhtxt.service.BxdService;
import com.glyxybxhtxt.service.QdbService;
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
        return new ResponseData(true);
    }

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
            bs.upbxdbyysr(b);
        }else{
            return new ResponseData("验收员id无效");
        }
        return new ResponseData(true);
    }

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
