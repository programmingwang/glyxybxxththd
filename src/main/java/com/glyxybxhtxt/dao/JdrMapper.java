package com.glyxybxhtxt.dao;


import com.glyxybxhtxt.dataObject.Jdr;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JdrMapper {
    int deleteByPrimaryKey(String ybid);

    int insert(Jdr record);

    int insertSelective(Jdr record);

    Jdr selectByPrimaryKey(String ybid);

    int updateByPrimaryKeySelective(Jdr record);

    int updateByPrimaryKey(Jdr record);

    List<Jdr> seljdrforpd();
    List<Jdr> selallJdr(@Param("states") List state);
    List<Jdr> selOptimalJdrPC(@Param("bxlb") String bxlb, @Param("eid")Integer eid);
    List<Jdr> selOptimalJdr(@Param("bxlb") String bxlb, @Param("eid")Integer eid);
}