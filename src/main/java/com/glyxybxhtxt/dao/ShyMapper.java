package com.glyxybxhtxt.dao;

import com.glyxybxhtxt.dataObject.Shy;

import java.util.List;


public interface ShyMapper {
    int deleteByPrimaryKey(String ybid);

    int insert(Shy record);

    int insertSelective(Shy record);

    Shy selectByPrimaryKey(String ybid);

    int updateByPrimaryKeySelective(Shy record);

    int updateByPrimaryKey(Shy record);
    
    List<Shy> selallshy();
    List<Shy> selOptimalshy(String xq);
    List<Shy> selqtshy(String xq);
    List<Shy> sellsqdshy(Integer eid);


}