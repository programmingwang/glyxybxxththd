package com.glyxybxhtxt.service;

import com.glyxybxhtxt.dataObject.DictItem;

import java.util.List;
import java.util.Map;

/**
 * @Author lrt
 * @Date 2020/11/2 16:46
 * @Version 1.0
 **/
public interface IDictService {

    List<DictItem> getDictListByCode(String dictCode);

    Map<String,String> getDictMapByCode(String dictCode);
}
