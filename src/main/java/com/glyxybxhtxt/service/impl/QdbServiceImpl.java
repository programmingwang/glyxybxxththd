package com.glyxybxhtxt.service.impl;

import com.glyxybxhtxt.dao.QdbMapper;
import com.glyxybxhtxt.dataObject.Qdb;
import com.glyxybxhtxt.service.QdbService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author:wangzh
 * Date: 2020/12/3 21:13
 * Version: 1.0
 */
@Service
public class QdbServiceImpl implements QdbService {
    @Resource
    private QdbMapper qdbMapper;

    @Override
    public List<Qdb> selallqy(Qdb q) {
        return qdbMapper.selqdb(q);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean qd(Qdb q) {
        return qdbMapper.qd(q) == 1;
    }

    @Override
    public String selectOptimalXq(String shyid) {
        return qdbMapper.selectOptimalXq(shyid);
    }

    @Override
    public String selectOptimalXqForShy(String shyid) {
        return qdbMapper.selectOptimalXqForShy(shyid);
    }
}
