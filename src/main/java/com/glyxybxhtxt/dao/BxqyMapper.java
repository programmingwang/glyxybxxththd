package com.glyxybxhtxt.dao;

import com.glyxybxhtxt.dataObject.Bxqy;

import java.util.List;


public interface BxqyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Bxqy record);

    int insertSelective(Bxqy record);

    Bxqy selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Bxqy record);

    int updateByPrimaryKey(Bxqy record);
    
    List<Bxqy> selallqy();
    List<Bxqy> ditu(String xq);

    Bxqy selByEwmId(Integer ewmId);
}