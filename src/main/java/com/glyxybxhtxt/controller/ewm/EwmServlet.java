package com.glyxybxhtxt.controller.ewm;

import com.alibaba.fastjson.JSON;
import com.glyxybxhtxt.dataObject.Bxqy;
import com.glyxybxhtxt.dataObject.Ewm;
import com.glyxybxhtxt.response.ResponseData;
import com.glyxybxhtxt.service.EwmService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author wzh
 * @Date 2021/8/26 11:40 下午
 * @Description
 */
@RestController
@Slf4j
public class EwmServlet {
    @Autowired
    private EwmService es;

    @RequestMapping("/EwmServlet")
    @ResponseBody
    ResponseData ewmServlet(@RequestParam("op")String op, @RequestParam(value = "ewmId", required = false) Integer eid) {
        if(StringUtils.isWhitespace(op) || StringUtils.isEmpty(op) || StringUtils.isBlank(op))
            return new ResponseData("2");
        switch (op){
            case "selEwmById" : return selEwmById(eid);
            default: return new ResponseData(false);
        }
    }

    @ResponseBody
    private ResponseData selEwmById(Integer eid){
        Map<String,Object> map = new HashMap<>();
        Ewm ewm = es.selById(eid);
        log.info("{} 查询到的二维码信息:{}",eid, JSON.toJSONString(ewm));
        map.put("ewmDetail", ewm);
        return new ResponseData(map);
    }
}
