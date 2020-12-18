package com.glyxybxhtxt.util;

import com.glyxybxhtxt.dataObject.DictItem;
import com.glyxybxhtxt.service.IDictService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author:wangzh
 * Date: 2020/12/14 4:25
 * Version: 1.0
 */
@Component
public class ParseBxlb {
    @Resource
    private IDictService dict;

    //解析bxlb参数为正常显示给前端用户的值
    public String paraseBxlb(String beforeBxlb){
        //bxlb12[0]是dict表中的数据，第一级，大类别，例如：物业维修，热水维修。。。
        //bxlb12[1]是第二级数据，bxlb12[1]才是真正要给前台用户展示的
        String[] bxlb12 = beforeBxlb.split("-");
        String bxlb1 = bxlb12[0];
        String bxlb2 = bxlb12[1];
        switch (bxlb1){
            case "1" : bxlb1 = "wywx"; break;
            case "2" : bxlb1 = "sdwx"; break;
            case "3" : bxlb1 = "rswx"; break;
            case "4" : bxlb1 = "jdwx"; break;
            case "5" : bxlb1 = "ktwx"; break;
            case "6" : bxlb1 = "qt"; break;
        }
        List<DictItem> xxbxlb = dict.getDictListByCode(bxlb1);
        for (DictItem dictItem : xxbxlb) {
            if(dictItem.getValue().equals(bxlb2)){
                return getzlb(bxlb1)+"-"+dictItem.getLabel();
            }
        }
        return "未找到的报修类别";
    }

    public static String getzlb(String bxlb1){
        switch (bxlb1){
            case "wywx" : bxlb1 = "物业维修"; break;
            case "sdwx" : bxlb1 = "水电维修"; break;
            case "rswx" : bxlb1 = "热水维修"; break;
            case "jdwx" : bxlb1 = "家电维修"; break;
            case "ktwx" : bxlb1 = "空调维修"; break;
            case "qt" : bxlb1 = "其他"; break;
        }
        return bxlb1;
    }
}
