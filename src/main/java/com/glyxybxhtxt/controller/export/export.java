package com.glyxybxhtxt.controller.export;

import com.glyxybxhtxt.service.ExportService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @Author lrt
 * @Date 2021/1/7 17:34
 * @Version 1.0
 * Describe 导出功能
 **/
@RestController
public class export {

    @Resource
    private ExportService exportService;

    @RequestMapping(value = "/export/{id}",method = RequestMethod.GET)
    public String export2xlsx(@PathVariable String id, HttpServletRequest request, HttpServletResponse response){
        String fileName = "export.xls";// 设置文件名，根据业务需要替换成要下载的文件名
        exportService.exportXlsx(id);
        if (fileName != null) {
            //设置文件路径
            String realPath = "src/main/resources/static/";
            File file = new File(realPath , fileName);
            if (file.exists()) {
                response.setContentType("application/octet-stream");//
                response.setHeader("content-type", "application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }
}
