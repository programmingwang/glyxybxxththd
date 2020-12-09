package com.glyxybxhtxt.controller.jdr;

import com.glyxybxhtxt.dataObject.Bxd;
import com.glyxybxhtxt.response.ResponseData;
import com.glyxybxhtxt.service.BxdService;
import org.apache.commons.lang.StringUtils;
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
public class JdrServlet{
    private static final long serialVersionUID = 1L;
    @Autowired
    private BxdService bs;

    @RequestMapping("/JdrServlet")
    @ResponseBody
    ResponseData jdrServlet(@RequestParam("op")String op, @RequestParam(value = "jid", required = false)String jid,
                            @RequestParam(value = "bid", required = false)String bid, @RequestParam(value = "state", required = false)String state,
                            @RequestParam(value = "hc", required = false)String hc, @RequestParam(value = "gs", required = false)String gs,
                            @RequestParam(value = "eid", required = false)String eid) {
        if(StringUtils.isWhitespace(op) || StringUtils.isEmpty(op) || StringUtils.isBlank(op))
            return new ResponseData("2");
        switch (op){
            case "selbxdbyjdr" : return selbxdbyjdr(jid, eid, state);
            case "upbxdbyjdr" : return upbxdbyjdr(jid, bid, state, hc, gs);
            case "selgs" : return selgs(jid);
            default: return new ResponseData(false);
        }
    }

    @ResponseBody
    private ResponseData selgs(String jid) {
        Map<String,Object> map = new HashMap<>();
        Double gs = bs.selgs(jid);
        if(gs==null||gs==0.0){
            map.put("gs", 0);
        }
        else{
            map.put("gs", gs);
        }
        return new ResponseData(map);
    }

    @ResponseBody
    private ResponseData upbxdbyjdr(String jid, String bid, String state, String hc, String gs) {
        ResponseData responseData = null;
        if(jid==null||bid==null){
            return new ResponseData("3");
        }
        Bxd b = new Bxd();
        b.setJid(jid);
        b.setId(Integer.parseInt(bid));
        if(state!=null){
            b.setState(Integer.parseInt(state));
            bs.upbxdbyjdr(b);
            responseData =new ResponseData("success","修改维修状态成功");
        }else{
            Bxd t = bs.selbxdforshyid(b.getId());
            if(t==null||t.getId()==null){
                responseData = new ResponseData("bidfalse");
            }
            if(!(0==t.getShy1state()||0==t.getShy2state())){
                responseData = new ResponseData("耗材及工时已审核，无法修改");
            }else{
                b.setHc(hc);
                b.setGs(gs);
                bs.upbxdbyjdr(b);
                responseData = new ResponseData("success","设置耗材、工时成功");
            }
        }
        return responseData;
    }

    @ResponseBody
    private ResponseData selbxdbyjdr(String jid, String eid, String state) {
        Map<String,Object> map = new HashMap<>();
        if(jid==null){
            return new ResponseData("3");
        }
        Bxd b = new Bxd();
        b.setJid(jid);
        if(eid!=null)b.setEid(Integer.parseInt(eid));
        if(state!=null)b.setState(Integer.parseInt(state));
        List<Bxd> blist = bs.selbxdbyjdr(b);
        map.put("blist",blist);
        return new ResponseData(map);
    }
}
