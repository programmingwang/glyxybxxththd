package com.glyxybxhtxt.util;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @Author lrt
 * @Date 2020/12/11 17:08
 * @Version 1.0
 **/
public class PathUtil {

    public static String getUploadPath() {
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (!path.exists()) path = new File("");
        File upload = new File(path.getAbsolutePath(), "static/bxdimg/");
        if (!upload.exists()) upload.mkdirs();
        return upload.getAbsolutePath();
    }

    public static void main(String[] args) {
        System.out.println(PathUtil.getUploadPath());
    }
}
