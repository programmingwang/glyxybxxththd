package com.glyxybxhtxt.service.impl;

import com.glyxybxhtxt.dao.JdrMapper;
import com.glyxybxhtxt.dataObject.Jdr;
import com.glyxybxhtxt.service.JdrService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author:wangzh
 * Date: 2020/12/3 21:11
 * Version: 1.0
 */
@Service
public class JdrServiceImpl implements JdrService {
    @Resource
    private JdrMapper jdrMapper;

    @Override
    public List<Jdr> selalljdr(String state) {
        return jdrMapper.selallJdr(state);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(String ybid) {
        jdrMapper.deleteByPrimaryKey(ybid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void newjdr(Jdr j) {
        jdrMapper.insertSelective(j);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void upjdr(Jdr j) {
        jdrMapper.updateByPrimaryKeySelective(j);
    }

    @Override
    public List<Jdr> selOptimaljdr(String bxlb, Integer eid) {
        return jdrMapper.selOptimalJdr(bxlb,eid);
    }
}
