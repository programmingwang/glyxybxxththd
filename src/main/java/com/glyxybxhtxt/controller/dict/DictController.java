package com.glyxybxhtxt.controller.dict;

import com.glyxybxhtxt.response.ResponseData;
import com.glyxybxhtxt.service.IDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author lrt
 * @Date 2020/11/2 16:25
 * @Version 1.0
 **/
@Api(tags = "获取字典值")
@RestController
@RequestMapping(value = "dict")
public class DictController {
    @Resource
    IDictService dictService;

    @RequestMapping(value = "/getDictByCode",method = RequestMethod.POST)
    public ResponseData getDictByCode(@ApiParam(value = "字典代码，对应dict表中的dict.DICT_CODE") @RequestParam String code){
        Map<String, Object> map = new HashMap<>();
        map.put("dictitem",dictService.getDictListByCode(code));
        return new ResponseData(map);
    }
}
