package com.glyxybxhtxt.service;

import com.glyxybxhtxt.dataObject.Shy;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * Author:wangzh
 * Date: 2020/12/3 21:06
 * Version: 1.0
 */
public interface ShyService {
    List<Shy> selallqy();
    void del(String ybid);
    void newshy(Shy s);
    void UPshy(Shy s);
    List<Shy> selOptimalShy(Integer eid);
    List<Shy> selqtShy(Integer eid);
    List<Shy> sellsqdshy(Integer eid);
    Shy selOneShy(String ybid);
}
