package com.glyxybxhtxt.service;

import com.glyxybxhtxt.dataObject.Qdb;

import java.util.List;

/**
 * Author:wangzh
 * Date: 2020/12/3 21:06
 * Version: 1.0
 */
public interface QdbService {
    List<Qdb> selallqy(Qdb q);
    boolean qd(Qdb q);
    String selectOptimalXq(String shyid);
    String selectOptimalXqForShy(String shyid);
}
