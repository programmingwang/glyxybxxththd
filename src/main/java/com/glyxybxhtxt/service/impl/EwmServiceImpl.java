package com.glyxybxhtxt.service.impl;

import com.glyxybxhtxt.dao.EwmMapper;
import com.glyxybxhtxt.dataObject.Ewm;
import com.glyxybxhtxt.service.EwmService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author:wangzh
 * Date: 2020/12/3 21:02
 * Version: 1.0
 */
@Service
public class EwmServiceImpl implements EwmService {
    @Resource
    private EwmMapper ewmMapper;

    /**
     * 查询一个二维码数据
     */
    @Override
    public List<Ewm> selewm(int qid) {
        return ewmMapper.selewm(qid);
    }

    @Override
    public Ewm selqyidbyewm(int eid) {
        return ewmMapper.selqyid(eid);
    }

    /**
     * 提供二维码id，查询该二维码和所在的报修区域的数据
     */
    @Override
    public Ewm selqybysbr(int eid) {
        return ewmMapper.selqybysbr(eid);
    }

    /**
     * 插入一个二维码
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean newewm(Ewm ew) {
        return ewmMapper.insert(ew) == 1;
    }

    /**
     * 更新一个二维码
     * @param ew
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean upewm(Ewm ew) {
        return ewmMapper.updateByPrimaryKeySelective(ew) == 1;
    }

    /**
     * 获得二维码的详细地点信息
     */
    @Override
    public String selxxwz(int eid) {
        StringBuffer ewmxxdd = new StringBuffer();
        Ewm ewm = ewmMapper.selxxwz(eid);
        ewmxxdd.append(ewm.getQy().getXq()).append(",").append(ewm.getQy().getQy()).append(",").append(ewm.getXxdd()).append(".");
        return ewmxxdd.toString();
    }

    @Override
    public Ewm selById(Integer eid) {
        return ewmMapper.selectByPrimaryKey(eid);
    }

    /**
     * 根据二维码id范围，更新范围内的二维码所处的区域
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updQidyAdmin(Integer qid, Integer startId, Integer endId) {
        return ewmMapper.updateQidByAdmin(qid, startId, endId);
    }
}
