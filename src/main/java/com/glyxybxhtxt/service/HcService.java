package com.glyxybxhtxt.service;

import com.glyxybxhtxt.dataObject.Hc;

import java.util.List;

/**
 * Author:wangzh
 * Date: 2020/12/3 21:06
 * Version: 1.0
 */
public interface HcService {
    List<Hc> selall();
    Hc selOneHc(Integer id);
    int delhc(int id);
    int newhc(Hc h);
    int uphc(Hc h);
}
