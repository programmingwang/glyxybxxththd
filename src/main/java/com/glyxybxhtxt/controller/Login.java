package com.glyxybxhtxt.controller;

import com.glyxybxhtxt.util.RealMe;
import com.glyxybxhtxt.util.UnicodeEncode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Author:wangzh
 * Date: 2020/12/4 16:34
 * Version: 1.0
 * Describe 登录
 */
@Controller
public class Login {

    private static final long serialVersionUID = 1L;

    @RequestMapping("/Login")
    public void login(HttpServletRequest request, HttpServletResponse response){
        try{
            HttpSession session = request.getSession();
            RealMe rm = (RealMe) session.getAttribute("realme");
            if(rm==null || rm.getSid()==null){
                response.sendRedirect("/index");
            }else{
                String eid = (String) session.getAttribute("eid");
                Service s = new Service();
                String xm = UnicodeEncode.unicodeEncode(rm.getRealName());
                System.out.println(xm);
                int sf = s.getsf(rm.getM().getId());
                if(eid!=null)
//                    response.sendRedirect("http://localhost/bxqt/#/index?xm="+xm+"&xh="+rm.getSid()+"&head="+rm.getM().getHead()+"&sf="+sf+"&ybid="+rm.getM().getId()+"&eid="+eid);
                    response.sendRedirect("https://yiban.glmc.edu.cn/bxqt/#/index?xm="+xm+"&xh="+rm.getSid()+"&head="+rm.getM().getHead()+"&sf="+sf+"&ybid="+rm.getM().getId()+"&eid="+eid);
                else
//                    response.sendRedirect("http://localhost/bxqt/#/index?xm="+xm+"&xh="+rm.getSid()+"&head="+rm.getM().getHead()+"&sf="+sf+"&ybid="+rm.getM().getId());
                    response.sendRedirect("https://yiban.glmc.edu.cn/bxqt/#/index?xm="+xm+"&xh="+rm.getSid()+"&head="+rm.getM().getHead()+"&sf="+sf+"&ybid="+rm.getM().getId());
            }
        }catch(Exception e){
            e.printStackTrace();
            try {
                response.sendRedirect("/index");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
