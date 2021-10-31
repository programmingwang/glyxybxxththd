package com.glyxybxhtxt.service.impl;

import com.glyxybxhtxt.dao.JdrMapper;
import com.glyxybxhtxt.dataObject.Jdr;
import com.glyxybxhtxt.service.JdrService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author:wangzh
 * Date: 2020/12/3 21:11
 * Version: 1.0
 * describe：接单人 Service
 */
@Service
public class JdrServiceImpl implements JdrService {
    /**
     * 接单人 Mapper
     */
    @Resource
    private JdrMapper jdrMapper;

    /**
     * 查询接单人的数据
     */
    @Override
    public List<Jdr> selalljdr(List state) {
        return jdrMapper.selallJdr(state);
    }

    /**
     * 根据接单人易班id，删除该接单人的数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(String ybid) {
        jdrMapper.deleteByPrimaryKey(ybid);
    }

    /**
     * 插入一条接单人的数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void newjdr(Jdr j) {
        jdrMapper.insertSelective(j);
    }

    /**
     * 更新一条接单人的数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void upjdr(Jdr j) {
        jdrMapper.updateByPrimaryKeySelective(j);
    }

    @Override
    public List<Jdr> selOptimaljdr(String bxlb, Integer eid) {
        return jdrMapper.selOptimalJdr(bxlb,eid);
    }

    @Override
    public List<Jdr> selOptimaljdrPC(String bxlb, Integer eid) {
        List<Jdr> jdrs = jdrMapper.selOptimalJdrPC(bxlb, eid);
        List<String> ybids = new ArrayList<>();
        List<Jdr> pcJdrs = jdrs.stream().filter(jdr -> {
            if (ybids.contains(jdr.getYbid())) {
                return false;
            } else {
                ybids.add(jdr.getYbid());
                return true;
            }
        }).collect(Collectors.toList());
        return pcJdrs;
    }
}
