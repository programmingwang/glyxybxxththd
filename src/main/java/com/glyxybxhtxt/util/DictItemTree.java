package com.glyxybxhtxt.util;

import com.glyxybxhtxt.dataObject.DictItem;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:wangzh
 * Date: 2020/12/17 11:41
 * Version: 1.0
 */
@Data
// 维修类别，存储如物业维修、热随维修、家电维修、空调维修等信息
public class DictItemTree implements Serializable {
    private String value;
    private String label;
    List<DictItem> children = new ArrayList<>();
}
