package com.glyxybxhtxt.controller;

import com.glyxybxhtxt.response.ResponseData;
import com.glyxybxhtxt.util.RealMe;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Author:wangzh
 * Date: 2020/12/4 16:35
 * Version: 1.0 
 */
@RestController
public class Info {
    private static final long serialVersionUID = 1L;

    /**
     * 从session获取用户的信息
     */
    @RequestMapping("/Info")
    @ResponseBody
    public ResponseData info(HttpServletRequest request) throws UnsupportedEncodingException {
        HttpSession session = request.getSession();
        RealMe rm = (RealMe) session.getAttribute("realme");
        if(rm==null || rm.getSid()==null){
            return new ResponseData("0");
        }else{
            Map<String,Object> map = new HashMap<>();
            map.put("rm", rm);
            return new ResponseData(map);
        }
    }
}
