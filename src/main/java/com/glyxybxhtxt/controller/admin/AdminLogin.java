package com.glyxybxhtxt.controller.admin;

import com.glyxybxhtxt.dataObject.Admin;
import com.glyxybxhtxt.response.ResponseData;
import com.glyxybxhtxt.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

/**
 * Author:wangzh
 * Date: 2020/12/4 17:26
 * Version: 1.0
 */
@RestController
public class AdminLogin {
    private static final long serialVersionUID = 1L;
    @Autowired
    private AdminService as;

    @PostMapping("/AdminLogin")
    @ResponseBody
    ResponseData adminLogin(@RequestParam("account")String account, @RequestParam("password")String password, HttpSession session) throws UnsupportedEncodingException {
        Admin s = new Admin();
        s.setAccount(account);
        s.setPassword(password);
        Admin temp = as.login(s);
        if(temp==null || temp.getId()==null){
            return new ResponseData(false);
        }else{
            session.setAttribute("admin", s);
            return new ResponseData(true);
        }
    }
}
