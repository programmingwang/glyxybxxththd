package com.glyxybxhtxt.controller.bxd;

import com.glyxybxhtxt.response.ResponseData;
import com.glyxybxhtxt.util.PathUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @Author lrt
 * @Date 2020/12/12 11:06
 * @Version 1.0
 **/
@RestController
public class VideoController {

    private static String PATH_FOLDER = PathUtil.getUploadPath();

    @PostMapping(value = "/video-upload")
    public ResponseData uploadVideo(@RequestParam("file") MultipartFile file){
        if (file.isEmpty()) {
            return new ResponseData("上传失败，请选择文件");
        }

        Date d = new Date();
        String time = String.valueOf(d.getTime()) + "v";
        String fileName = time + "." + file.getOriginalFilename().split("\\.")[1];
        File dest = new File(PATH_FOLDER + "/" + fileName);
        System.out.println(PATH_FOLDER + "/" + fileName);
        try {
            file.transferTo(dest);
            return new ResponseData("true","上传成功",fileName);
        } catch (IOException e) {
        }
        return new ResponseData("上传失败！");
    }

    @DeleteMapping(value = "delvideo")
    public ResponseData delVideo(@RequestParam String fileName){
        String reg = "(mp4|flv|avi|rm|rmvb|wmv)";
        Pattern p = Pattern.compile(reg);
        boolean isMpeg = p.matcher(fileName.split("\\.")[1]).find();
        if (!isMpeg){
            return new ResponseData("false", "非法请求");
        }
        String filePath = PATH_FOLDER + "/" + fileName;
        File file = new File(filePath);

        if (file.exists()){
            boolean result = file.delete();
            if (result) {
                return new ResponseData("true","成功");
            }else{
                return new ResponseData("false", "失败");
            }
        }
        return new ResponseData("false", "失败");
    }
}
