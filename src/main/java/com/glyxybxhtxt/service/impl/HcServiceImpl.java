package com.glyxybxhtxt.service.impl;

import com.glyxybxhtxt.dao.HcMapper;
import com.glyxybxhtxt.dataObject.Hc;
import com.glyxybxhtxt.service.HcService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author:wangzh
 * Date: 2020/12/3 21:07
 * Version: 1.0
 * describe：耗材 Service
 */
@Service
public class HcServiceImpl implements HcService {
    /**
     * 耗材 Mapper
     */
    @Resource
    private HcMapper hcMapper;

    /**
     * 查询所有耗材表的数据
     */
    @Override
    public List<Hc> selall() {
        return hcMapper.selall();
    }

    /**
     * 根据一级类别和二级类别获取耗材表的数据
     * @param yjlb 一级类别
     * @param ejlb 二级类别
     */
    @Override
    public List<Hc> selHcByLb(String yjlb, String ejlb) {
        return hcMapper.selHcByLb(yjlb, ejlb);
    }

    /**
     * 根据耗材id，获取该耗材的数据
     */
    @Override
    public Hc selOneHc(Integer id) {
        return hcMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据耗材id，删除该耗材的数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delhc(int id) {
        return hcMapper.deleteByPrimaryKey(id);
    }

    /**
     * 插入一条耗材数据到耗材表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int newhc(Hc h) {
        return hcMapper.insert(h);
    }

    /**
     * 根据耗材id，更新该耗材的数据
     * 有条件，看sql
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int uphc(Hc h) {
        return hcMapper.updateByPrimaryKeySelective(h);
    }
}
