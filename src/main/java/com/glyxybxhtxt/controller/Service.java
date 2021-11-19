package com.glyxybxhtxt.controller;


import com.glyxybxhtxt.dao.JdrMapper;
import com.glyxybxhtxt.dao.ShyMapper;
import com.glyxybxhtxt.dataObject.Jdr;
import com.glyxybxhtxt.dataObject.Shy;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author:wangzh
 * Date: 2020/12/4 16:34
 * Version: 1.0
 */
public class Service {
    @Autowired
    private ShyMapper mapper;
    @Autowired
    private JdrMapper mapper2;

    /**
     * 通过易班id，获取该id的个人信息，并通过工号判断属于哪类人
     * 1：申报人
     * 2：接单人
     * 3：审核员
     */
    public int getsf(String ybid){
        Shy s = mapper.selectByPrimaryKey(ybid);
        Jdr j = mapper2.selectByPrimaryKey(ybid);
        if(s!=null){
            if(s.getGh()!=null)return 3;
        }else if(j!=null){
            if(j.getGh()!=null)return 2;
        }
        return 1;
    }
}
