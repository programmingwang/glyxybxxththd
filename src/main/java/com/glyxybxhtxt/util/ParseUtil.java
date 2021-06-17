package com.glyxybxhtxt.util;

import com.glyxybxhtxt.dataObject.DictItem;
import com.glyxybxhtxt.dataObject.Hc;
import com.glyxybxhtxt.service.HcService;
import com.glyxybxhtxt.service.IDictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Author:wangzh
 * Date: 2020/12/14 4:25
 * Version: 1.0
 */
@Component
@SuppressWarnings("all")
public class ParseUtil {
    @Resource
    private IDictService dict;

    @Resource
    private HcService hs;

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

    /**
     *
     * @param beforeHc 是数据库里的耗材表达
     * @return 解析hc为显示给用户的正常值
     */
    public String paraseHc(String beforeHc){
        //需要给用户展示的字符串
        StringBuffer afterHc = new StringBuffer();
        StringBuffer fghczd = new StringBuffer("返工耗材：");
        if(StringUtils.isBlank(beforeHc)){
            return "";
        }
        //hc分类，hcfl[0]是正常订单的耗材
        //hcfl[0]之后的都是返工的耗材
        List<String> hcfl = Arrays.asList(beforeHc.split("\\|返工耗材:"));
        int i = 0;
        for (String allhc : hcfl) {

            //将多个耗材分割出来，例如2-1|3-2 分割成 2-1，3-2；做hc表的查询
            List<String> hcs = Arrays.asList(allhc.split("\\|"));
            for (String hc : hcs) {
                //提取第一个做查询例如1-2，提取1，这个1是hc表的id,查询到的是具体的耗材类
                Hc xxhc = hs.selOneHc(Integer.parseInt(hc.split("-")[0]));
                if(i == 0){
                    afterHc.append(xxhc.getMc()).append("(型号:")
                            .append(xxhc.getXh()).append(")").append("¥");
                }else{
                    afterHc.append(fghczd).append(xxhc.getMc()).append("(型号:")
                            .append(xxhc.getXh()).append(")").append("¥");
                }

            }
            i++;

        }
        return afterHc.substring(0,afterHc.length()-1);
    }

    public String paraseHc1(String beforeHc){
        //需要给用户展示的字符串
        StringBuffer afterHc = new StringBuffer();
        StringBuffer fghczd = new StringBuffer("返工耗材：");
        if(StringUtils.isBlank(beforeHc)){
            return "";
        }
        //hc分类，hcfl[0]是正常订单的耗材
        //hcfl[0]之后的都是返工的耗材
        List<String> hcfl = Arrays.asList(beforeHc.split("\\|返工耗材:"));
        int i = 0;
        for (String allhc : hcfl) {

            //将多个耗材分割出来，例如2-1|3-2 分割成 2-1，3-2；做hc表的查询
            List<String> hcs = Arrays.asList(allhc.split("\\|"));
            for (String hc : hcs) {
                //提取第一个做查询例如1-2，提取1，这个1是hc表的id,查询到的是具体的耗材类
                Hc xxhc = hs.selOneHc(Integer.parseInt(hc.split("-")[0]));
                if(i == 0){
                    afterHc.append(xxhc.getMc()).append("(型号:")
                            .append(xxhc.getXh()).append(";数量:").append(hc.split("-")[1]).append(xxhc.getDw()).append(")").append("¥");
                }else{
                    afterHc.append(fghczd).append(xxhc.getMc()).append("(型号:")
                            .append(xxhc.getXh()).append(";数量:").append(hc.split("-")[1]).append(xxhc.getDw()).append(")").append("¥");
                }

            }
            i++;

        }
        return afterHc.substring(0,afterHc.length()-1);
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
