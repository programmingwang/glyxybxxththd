package com.glyxybxhtxt.service.impl;

import com.glyxybxhtxt.dao.BxqyMapper;
import com.glyxybxhtxt.dataObject.Bxqy;
import com.glyxybxhtxt.service.BxqyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author:wangzh
 * Date: 2020/12/3 20:39
 * Version: 1.0
 * Describe：保修区域 service
 */
@Service
public class BxqyServiceImpl implements BxqyService {
    @Resource
    private BxqyMapper bxqyMapper;

    /**
     * 根据报修区域id，获取该报修区域的数据
     */
    @Override
    public Bxqy selbxqy(Integer id) {
        return bxqyMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取所有报修区域的数据
     */
    @Override
    public List<Bxqy> selallqy() {
        return bxqyMapper.selallqy();
    }

    /**
     * 获取某个报修区域的数据
     */
    @Override
    public List<Bxqy> ditu(String xq) {
        return bxqyMapper.ditu(xq);
    }

    /**
     * 插入一条报修区域的数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean newqy(Bxqy q) {
        return bxqyMapper.insert(q) == 1;
    }

    /**
     * 更新一条报修区域的数据
     * 有条件，看sql
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean upqy(Bxqy q) {
        return bxqyMapper.updateByPrimaryKeySelective(q) == 1;
    }

    /**
     * 根据二维码id，查询该二维码数据
     */
    @Override
    public Bxqy selByEwmId(Integer eid) {
        return bxqyMapper.selByEwmId(eid);
    }
}
