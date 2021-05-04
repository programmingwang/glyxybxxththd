package com.glyxybxhtxt.service;

import com.glyxybxhtxt.dataObject.Jdr;

import java.util.List;

/**
 * Author:wangzh
 * Date: 2020/12/3 21:06
 * Version: 1.0
 */
public interface JdrService {
    List<Jdr> selalljdr(List state);
    void del(String ybid);
    void newjdr(Jdr j);
    void upjdr(Jdr j);
    List<Jdr> selOptimaljdr(String bxlb, Integer eid);
    List<Jdr> selOptimaljdrPC(String bxlb, Integer eid);
}
