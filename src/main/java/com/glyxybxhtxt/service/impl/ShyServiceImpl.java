package com.glyxybxhtxt.service.impl;

import com.glyxybxhtxt.dao.ShyMapper;
import com.glyxybxhtxt.dataObject.Shy;
import com.glyxybxhtxt.service.ShyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author:wangzh
 * Date: 2020/12/3 21:14
 * Version: 1.0
 */
@Service
public class ShyServiceImpl implements ShyService{
    @Resource
    private ShyMapper shyMapper;


    @Override
    public List<Shy> selallqy() {
        return shyMapper.selallshy();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(String ybid) {
        shyMapper.deleteByPrimaryKey(ybid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void newshy(Shy s) {
        shyMapper.insertSelective(s);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void UPshy(Shy s) {
        shyMapper.updateByPrimaryKeySelective(s);
    }

    @Override
    public List<Shy> selOptimalShy(Integer eid) {
        return shyMapper.selOptimalshy(eid);
    }
}
