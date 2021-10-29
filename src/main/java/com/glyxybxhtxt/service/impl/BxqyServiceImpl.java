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
 */
@Service
public class BxqyServiceImpl implements BxqyService {
    @Resource
    private BxqyMapper bxqyMapper;

    @Override
    public Bxqy selbxqy(Integer id) {
        return bxqyMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Bxqy> selallqy() {
        return bxqyMapper.selallqy();
    }

    @Override
    public List<Bxqy> ditu(String xq) {
        return bxqyMapper.ditu(xq);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean newqy(Bxqy q) {
        return bxqyMapper.insert(q) == 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean upqy(Bxqy q) {
        return bxqyMapper.updateByPrimaryKeySelective(q) == 1;
    }

    @Override
    public Bxqy selByEwmId(Integer eid) {
        return bxqyMapper.selByEwmId(eid);
    }
}
