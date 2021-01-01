package com.glyxybxhtxt.service;

import com.glyxybxhtxt.dataObject.Ewm;

import java.util.List;

/**
 * Author:wangzh
 * Date: 2020/12/3 21:02
 * Version: 1.0
 */
public interface EwmService {
    List<Ewm> selewm(int qid);
    Ewm selqyidbyewm(int eid);
    Ewm selqybysbr(int eid);
    boolean newewm(Ewm ew);
    boolean upewm(Ewm ew);
    //获得二维码的详细地点信息
    String selxxwz(int eid);
}
