package com.glyxybxhtxt.service;

import com.glyxybxhtxt.dataObject.Bxd;
import com.glyxybxhtxt.dataObject.Jdr;

import java.util.List;

/**
 * Author:wangzh
 * Date: 2020/12/3 20:11
 * Version: 1.0
 */
public interface BxdService {
    List<Bxd> selforsbr(Bxd b);
    Bxd selbxdforshyid(Integer id);
    List<Bxd> selbxdbyadmin(Bxd b);
    List<Bxd> selbxdforeid(Integer eid);
    List<Bxd> selbxdbyjdr(Bxd b);
    List<Bxd> selbxdbyshy(Bxd b);
    void upbxdbysbr(Bxd b);
    void upbxd1byshy(Bxd b);
    void upbxd2byshy(Bxd b);
    int upbxdhcbyshy(Bxd b);
    void upbxdbyadmin(Bxd b);
    void upbxdbyjdr(Bxd b);
    void del(Integer id);
    int newbxdbysbr(Bxd b);
    int selnumforstate(int b);
    int selnumforpj(int b);
    int allcount();
    Double selgs(String jid);
    String tj();
    int getsbrbxdnewid(String xh);
    List<Jdr> seljdrforpd();
    int upbxdbyysr(Bxd b);
}
