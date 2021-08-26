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

    @Override
    public List<Ewm> selewm(int qid) {
        return ewmMapper.selewm(qid);
    }

    @Override
    public Ewm selqyidbyewm(int eid) {
        return ewmMapper.selqyid(eid);
    }

    @Override
    public Ewm selqybysbr(int eid) {
        return ewmMapper.selqybysbr(eid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean newewm(Ewm ew) {
        return ewmMapper.insert(ew) == 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean upewm(Ewm ew) {
        return ewmMapper.updateByPrimaryKeySelective(ew) == 1;
    }



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
}
