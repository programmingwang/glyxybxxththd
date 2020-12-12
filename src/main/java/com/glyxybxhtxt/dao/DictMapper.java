package com.glyxybxhtxt.dao;


import com.glyxybxhtxt.dataObject.DictItem;

import java.util.List;

/**
 * @Author lrt
 * @Date 2020/11/2 17:09
 * @Version 1.0
 **/
public interface DictMapper {
    List<DictItem> selectDictItemByCode(String dictCode);
}
