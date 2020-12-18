package com.glyxybxhtxt.controller.hc;

import com.glyxybxhtxt.dataObject.Hc;
import com.glyxybxhtxt.response.ResponseData;
import com.glyxybxhtxt.service.HcService;
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
 * Date: 2020/12/4 15:52
 * Version: 1.0
 */
@RestController
public class HcServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    private HcService hs;

    @RequestMapping("/HcServlet")
    @ResponseBody
    ResponseData hcServlet(@RequestParam("op")String op, @RequestParam(value = "mc",required = false)String mc,
                           @RequestParam(value = "dw",required = false)String dw, @RequestParam(value = "jg",required = false)String jg,
                           @RequestParam(value = "id",required = false)String id, @RequestParam(value = "lb", required = false)String lb,
                           @RequestParam(value = "xh",required = false)String xh){
        if(StringUtils.isWhitespace(op) || StringUtils.isEmpty(op) || StringUtils.isBlank(op))
            return new ResponseData("2");
        switch (op){
            case "selhc" : return selhc();
            case "delhc" : return delhc(id);
            case "newhc" : return newhc(mc,dw,jg,lb,xh);
            case "uphc" : return uphc(id,mc,dw,jg,lb,xh);
            default: return new ResponseData(false);
        }
    }

    @ResponseBody
    private ResponseData newhc(String mc, String dw, String jg,String lb,String xh){
        Hc h = new Hc();
        h.setMc(mc);
        h.setDw(dw);
        h.setJg(Double.parseDouble(jg));
        h.setLb(lb);
        h.setXh(xh);
        return hs.newhc(h)==1 ? new ResponseData(true) :new ResponseData("db error");

    }

    @ResponseBody
    private ResponseData uphc(String id, String mc, String dw, String jg,String lb,String xh) {
        Hc h = new Hc();
        h.setId(Integer.parseInt(id));
        if(mc!=null) h.setMc(mc);
        if(dw!=null) h.setDw(dw);
        if(jg!=null) h.setJg(Double.parseDouble(jg));
        if (lb != null) h.setLb(lb);
        if (xh != null) h.setXh(xh);
        return hs.uphc(h)==1 ? new ResponseData(true) :new ResponseData("db error");
    }

    private ResponseData delhc(String id) {
        return hs.delhc(Integer.parseInt(id))==1 ? new ResponseData(true) :new ResponseData("db error");
    }

    private ResponseData selhc() {
        Map<String,List> map = new HashMap<>();
        map.put("hlist", hs.selall());
        return new ResponseData(map);
    }
}
