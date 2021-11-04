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

    /**
     * 根据审核员id，查询该审核员的所有签到表
     */
    @Override
    public List<Qdb> selallqy(Qdb q) {
        return qdbMapper.selqdb(q);
    }

    /**
     * 插入一条审核员的签到数据到签到表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean qd(Qdb q) {
        return qdbMapper.qd(q) == 1;
    }

    /**
     * 根据审核员id，从签到表查询该审核员签到的校区地点
     * 前提：审核员在职
     */
    @Override
    public String selectOptimalXq(String shyid) {
        return qdbMapper.selectOptimalXq(shyid);
    }

    /**
     * 根据审核员id，从签到表查询该审核员签到的校区地点
     * 和上方方法比较：该方法不管审核员是否在职
     */
    @Override
    public String selectOptimalXqForShy(String shyid) {
        return qdbMapper.selectOptimalXqForShy(shyid);
    }
}
