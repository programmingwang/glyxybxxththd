package com.glyxybxhtxt.service.impl;

import com.glyxybxhtxt.constant.GlmcConstants;
import com.glyxybxhtxt.dao.BxdMapper;
import com.glyxybxhtxt.dao.JdrMapper;
import com.glyxybxhtxt.dataObject.Bxd;
import com.glyxybxhtxt.dataObject.Jdr;
import com.glyxybxhtxt.service.BxdService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author:wangzh
 * Date: 2020/12/3 20:17
 * Version: 1.0
 */
@Service
public class BxdServiceImpl implements BxdService {
    @Resource
    private BxdMapper bxdMapper;

    @Resource
    private JdrMapper jdrMapper;

    @Override
    public List<Bxd> selforsbr(Bxd b) {
        return bxdMapper.selforsbr(b);
    }

    @Override
    public Bxd selbxdforshyid(Integer id) {
        return bxdMapper.selbxdforshyid(id);
    }

    @Override
    public List<Bxd> selbxdbyadmin(Bxd b) {
        return bxdMapper.selbxdbyadmin(b);
    }

    @Override
    public List<Bxd> selbxdforeid(Integer eid) {
        return bxdMapper.selbxdforeid(eid);
    }

    @Override
    public List<Bxd> selbxdbyjdr(Bxd b) {
        return bxdMapper.selbxdbyjdr(b);
    }

    @Override
    public List<Bxd> selbxdbyshy(Bxd b) {
        return bxdMapper.selbxdbyshy(b);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void upbxdbysbr(Bxd b) {
        bxdMapper.updateByPrimaryKeySelectivebysbr(b);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void upbxd1byshy(Bxd b) {
        bxdMapper.upbxd1byshy(b);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void upbxd2byshy(Bxd b) {
        bxdMapper.upbxd2byshy(b);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int upbxdhcbyshy(Bxd b) {
        return bxdMapper.upbxdhcbyshy(b);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void upbxdbyadmin(Bxd b) {
        bxdMapper.upbxdbyadmin(b);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void fg(Bxd b) {
        bxdMapper.fg(b);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void upbxdbyjdr(Bxd b) {
        bxdMapper.upbxdbyjdr(b);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(Integer id) {
        bxdMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int newbxdbysbr(Bxd b) {
        return bxdMapper.insert(b);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int selnumforstate(int b) {
        return bxdMapper.selnumforstate(b);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int selnumforpj(int b) {
        return bxdMapper.selnumforpj(b);
    }

    @Override
    public int allcount() {
        return bxdMapper.allcount();
    }

    @Override
    public Double selgs(String jid) {
        return bxdMapper.selgs(GlmcConstants.GS, jid);
    }

    @Override
    public String tj() {
        JSONObject json = new JSONObject();
        json.put("daybx", bxdMapper.daybx());
        json.put("daywx", bxdMapper.daywx());
        json.put("monthwx", bxdMapper.monthwx());
        json.put("monthbx", bxdMapper.monthbx());
        json.put("yearwx", bxdMapper.yearwx());
        json.put("yearbx", bxdMapper.yearbx());
        return json.toString();
    }

    @Override
    public int getsbrbxdnewid(String xh) {
        return bxdMapper.getsbrbxdnewid(xh);
    }

    @Override
    public List<Jdr> seljdrforpd() {
        return jdrMapper.seljdrforpd();
    }

    @Override
    public int upbxdbyysr(Bxd b) {
        return bxdMapper.upbxdbyysr(b);
    }

    @Override
    public String selishc(Integer id) {
        return bxdMapper.selishc(id);
    }

    @Override
    public Bxd selonebxd(Integer id) {
        return bxdMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Bxd> xxsjBxd() {
        return bxdMapper.xxsjbxd();
    }

    @Override
    public List<Bxd> selwpdbxd() {
        return bxdMapper.selwpdbxd();
    }
}
