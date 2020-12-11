package com.glyxybxhtxt;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author:wangzh
 * Date: 2020/12/10 15:57
 * Version: 1.0
 */
@Controller
public class IndexController {
    @RequestMapping("/index")
    public String loginjsp(){
        return "/index";
    }
}
