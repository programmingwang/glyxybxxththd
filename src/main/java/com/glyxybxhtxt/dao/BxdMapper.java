package com.glyxybxhtxt.dao;

import com.glyxybxhtxt.dataObject.Bxd;

import java.util.List;

public interface BxdMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Bxd record);

    int insertSelective(Bxd record);

    Bxd selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Bxd record);

    int updateByPrimaryKey(Bxd record);

    List<Bxd> selforsbr(Bxd b);
    int updateByPrimaryKeySelectivebysbr(Bxd record);
    int getsbrbxdnewid(String xh);
    List<Bxd> selbxdbyadmin(Bxd b);
    List<Bxd> selbxdbyshy(Bxd b);
    List<Bxd> selbxdbyjdr(Bxd b);
    List<Bxd> selbxdforeid(Integer eid);
    int upbxdbyadmin(Bxd b);
    int fg(Bxd b);
    int upbxdbyjdr(Bxd b);
    int upbxd1byshy(Bxd b);
    int upbxd2byshy(Bxd b);
    int upbxdhcbyshy(Bxd b);
    Bxd selbxdforshyid(Integer id);
    int selnumforstate(int state);
    int selnumforpj(int pj);
    int allcount();
    int daybx();
    int monthbx();
    int yearbx();
    int daywx();
    int monthwx();
    int yearwx();
    Double selgs(String jid);
    int upbxdbyysr(Bxd b);
    String selishc(Integer id);
    //未上班时间申报的报修单
    List<Bxd> xxsjbxd();
}