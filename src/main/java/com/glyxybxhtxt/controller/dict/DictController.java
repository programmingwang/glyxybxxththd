package com.glyxybxhtxt.controller.dict;

import com.glyxybxhtxt.dataObject.DictItem;
import com.glyxybxhtxt.response.ResponseData;
import com.glyxybxhtxt.service.IDictService;
import com.glyxybxhtxt.util.DictItemTree;
import com.glyxybxhtxt.util.ParseUtil;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author lrt
 * @Date 2020/11/2 16:25
 * @Version 1.0
 **/
@Api(tags = "获取wxlb")
@RestController
@RequestMapping(value = "dict")
public class DictController {
    @Resource
    IDictService dictService;

    /**
     * 获取维修类别
     * 根据字符串解析为中文
     */
    @RequestMapping(value = "/getAllWxlb",method = RequestMethod.GET)
    public ResponseData getAllWxlb(){
        //所有的大类别 dict表中
        List<String> wylb = Arrays.asList("wywx","sdwx","rswx","jdwx","ktwx","qt");
        List<DictItemTree> option = new ArrayList<>();
        for (int i = 0; i < wylb.size(); i++) {
            DictItem dictItem = new DictItem();
            DictItemTree dictItemTree = new DictItemTree();
            //设置大类别的value
            dictItemTree.setValue(String.valueOf(i+1));
            //设置大类别的label
            dictItemTree.setLabel(ParseUtil.getzlb(wylb.get(i)));
            //设置小类别
            List<DictItem> children = new ArrayList<>(dictService.getDictListByCode(wylb.get(i)));
            dictItemTree.setChildren(children);
            option.add(dictItemTree);
        }


        return new ResponseData(option);
    }
}
