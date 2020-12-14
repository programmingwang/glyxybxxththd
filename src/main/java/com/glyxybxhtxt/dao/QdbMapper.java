package com.glyxybxhtxt.dao;

import com.glyxybxhtxt.dataObject.Qdb;

import java.util.List;


public interface QdbMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Qdb record);

    int insertSelective(Qdb record);

    Qdb selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Qdb record);

    int updateByPrimaryKey(Qdb record);
    
    List<Qdb> selqdb(Qdb q);
    int qd(Qdb q);
    String selectOptimalXq(String shyid);
    String selectOptimalXqForShy(String shyid);
}