package com.glyxybxhtxt.controller.bxd;

import com.glyxybxhtxt.dataObject.Bxd;
import com.glyxybxhtxt.service.BxdService;
import net.sf.json.JSONObject;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Author:wangzh
 * Date: 2020/12/4 16:35
 * Version: 1.0
 */
@Controller
public class Fileload extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String PATH_FOLDER = "/";
    @Autowired
    private BxdService bs;

    public void init(ServletConfig config) throws ServletException {
        ServletContext servletCtx = config.getServletContext();
        PATH_FOLDER = servletCtx.getRealPath("bxdimg");
    }

    @RequestMapping("/Fileload")
    public void fileLoad(HttpServletRequest request, HttpServletResponse response){
        try {
            String eid = " ";
            String xxdd = " ";
            String yysj = " ";
            String bxlb = " ";
            String bxnr = " ";
            String sbrsj = " ";
            String sbrxh = " ";
            String sbr = " ";
            String savaFileName = "";
            String filename = "";

            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items;
            items = upload.parseRequest(new ServletRequestContext(request));
            for (FileItem item : items)
            {
                if(item.isFormField()){
                    String key=item.getFieldName();
                    String value=item.getString();
                    if("eid".equals(key)){
                        eid = new String(value.getBytes("ISO-8859-1"),"UTF-8");
                    }
                    if("xxdd".equals(key))
                    {
                        xxdd = new String(value.getBytes("ISO-8859-1"),"UTF-8");
                    }
                    if("yysj".equals(key))
                    {
                        yysj = new String(value.getBytes("ISO-8859-1"),"UTF-8");
                    }
                    if("bxlb".equals(key))
                    {
                        bxlb = new String(value.getBytes("ISO-8859-1"),"UTF-8");
                    }
                    if("bxnr".equals(key))
                    {
                        bxnr = new String(value.getBytes("ISO-8859-1"),"UTF-8");
                    }
                    if("sbrsj".equals(key))
                    {
                        sbrsj = new String(value.getBytes("ISO-8859-1"),"UTF-8");
                    }
                    if("sbrxh".equals(key))
                    {
                        sbrxh = new String(value.getBytes("ISO-8859-1"),"UTF-8");
                    }
                    if("sbr".equals(key))
                    {
                        sbr = new String(value.getBytes("ISO-8859-1"),"UTF-8");
                    }
                }else{
                    Date d = new Date();
                    String address = PATH_FOLDER;
                    String value = item.getName();
                    if(value==null || "".equals(value))
                        break;
                    String[] ss = value.split("\\.");
                    savaFileName = "@"+d.getTime()+"."+ss[ss.length-1];
                    File f=new File(address);
                    f.mkdirs();
                    item.write(new File(f+"\\"+savaFileName));
                    filename = filename + "|" + savaFileName;
                }
            }
            Bxd b = new Bxd();
            b.setEid(Integer.parseInt(eid));
            b.setXxdd(xxdd);
            b.setYysj(yysj);
            b.setBxlb(bxlb);
            b.setBxnr(bxnr);
            b.setSbr(sbr);
            b.setSbrsj(sbrsj);
            b.setSbrxh(sbrxh);
            bs.newbxdbysbr(b);
            response.sendRedirect("https://yiban.gxun.edu.cn/bxqt/#/declare-eid-record/"+eid);
        }catch(Exception e){
            e.printStackTrace();
            JSONObject json = new JSONObject();
            try {
                response.sendRedirect("localhost/bxqt/#/");
				/*json.put("status", "false");
				json.put("info", "3");*/
                response.getWriter().print(json.toString());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }
}
