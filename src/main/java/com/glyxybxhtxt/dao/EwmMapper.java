package com.glyxybxhtxt.dao;

import com.glyxybxhtxt.dataObject.Ewm;

import java.util.List;


public interface EwmMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Ewm record);

    int insertSelective(Ewm record);

    Ewm selectByPrimaryKey(Integer id);
    //查询二维码详细位置
    Ewm selxxwz(Integer id);

    int updateByPrimaryKeySelective(Ewm record);

    int updateByPrimaryKey(Ewm record);
    
    List<Ewm> selewm(int qid);
    Ewm selqyid(int eid);
    Ewm selqybysbr(int eid);
}