package com.glyxybxhtxt.dao;

import com.glyxybxhtxt.dataObject.Hc;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HcMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Hc record);

    int insertSelective(Hc record);

    Hc selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Hc record);

    int updateByPrimaryKey(Hc record);
    List<Hc> selall();
    List<Hc> selHcByLb(@Param("yjlb")String yjlb, @Param("ejlb")String ejlb);
}