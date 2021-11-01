package com.glyxybxhtxt.service.impl;

import com.alibaba.fastjson.JSON;
import com.glyxybxhtxt.dao.ShyMapper;
import com.glyxybxhtxt.dataObject.Bxqy;
import com.glyxybxhtxt.dataObject.Shy;
import com.glyxybxhtxt.service.BxqyService;
import com.glyxybxhtxt.service.EwmService;
import com.glyxybxhtxt.service.ShyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j
public class ShyServiceImpl implements ShyService{
    @Resource
    private ShyMapper shyMapper;

    @Autowired
    private BxqyService bxqyService;



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
        //根据ewmId线查询当前校区
        Bxqy bxqy = bxqyService.selByEwmId(eid);
        log.info("二维码id是{}的报修区域是{}", eid, JSON.toJSONString(bxqy));
        String xq = bxqy.getXq();
        return shyMapper.selOptimalshy(xq);
    }

    @Override
    public List<Shy> selqtShy(Integer eid) {
        //根据ewmId线查询当前校区
        Bxqy bxqy = bxqyService.selByEwmId(eid);
        log.info("二维码id是{}的报修区域是{}", eid, JSON.toJSONString(bxqy));
        String xq = bxqy.getXq();
        return shyMapper.selqtshy(xq);
    }

    @Override
    public List<Shy> sellsqdshy(Integer eid) {
        //根据ewmId线查询当前校区
        Bxqy bxqy = bxqyService.selByEwmId(eid);
        log.info("二维码id是{}的报修区域是{}", eid, JSON.toJSONString(bxqy));
        String xq = bxqy.getXq();
        return shyMapper.sellsqdshy(xq);
    }

    @Override
    public Shy selOneShy(String ybid) {
        return shyMapper.selectByPrimaryKey(ybid);
    }
}
