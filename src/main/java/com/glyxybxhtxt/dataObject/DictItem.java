package com.glyxybxhtxt.dataObject;

import lombok.Data;

import java.io.Serializable;


/**
 * @Author lrt
 * @Date 2020/11/2 17:12
 * @Version 1.0
 **/
@Data
/**
 * 维修具体内容
 * 注意：维修分为两大类，第一大类是总体概括。有：
 * 1：物业维修
 * 2：水电维修
 * 3：热水维修
 * 4：家电维修
 * 5：空调维修
 * 6：其他
 * 
 * 第二大类是第一大类的详细内容。太多无法说明，举例
 * 物业维修的详细内容：家具、腻子、墙砖......
 * 水电维修的详细内容：洗手池、马桶......
 * 具体看数据库的 dictitem 表
 */
public class DictItem implements Serializable {
    /**
     * value是报修类型的具体内容，以数字表示
     * 如1代表家具，2代表腻子
     * 维修类型如下：
     */
    private String value;
    /**
     * 报修类型的具体内容
     * 如物业报修有家具、地砖等维修
     * 水电维修有水龙头、阀门等维修
     */
    private String label;
}
