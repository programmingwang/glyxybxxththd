package com.glyxybxhtxt.service;

import com.glyxybxhtxt.dataObject.Jdr;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * Author:wangzh
 * Date: 2020/12/3 21:06
 * Version: 1.0
 */
public interface JdrService {
    List<Jdr> selalljdr(String state);
    void del(String ybid);
    void newjdr(Jdr j);
    void upjdr(Jdr j);
    List<Jdr> selOptimaljdr(String bxlb, Integer eid);
}
