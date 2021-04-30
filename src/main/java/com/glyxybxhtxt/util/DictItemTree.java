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
public class DictItemTree implements Serializable {
    private String value;
    private String label;
    List<DictItem> children = new ArrayList<>();
}
