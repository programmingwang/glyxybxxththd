package com.glyxybxhtxt.controller.bxd;

import com.glyxybxhtxt.dataObject.Bxd;
import com.glyxybxhtxt.dataObject.Ewm;
import com.glyxybxhtxt.response.ResponseData;
import com.glyxybxhtxt.service.BxdService;
import com.glyxybxhtxt.service.EwmService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:wangzh
 * Date: 2020/12/4 14:54
 * Version: 1.0
 */
@RestController
public class BxdServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String PATH_FOLDER = "/";
    @Autowired
    private BxdService bs;
    @Autowired
    private EwmService es;


    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletCtx = config.getServletContext();
        PATH_FOLDER = servletCtx.getRealPath("/bxdimg");
    }

    @RequestMapping("/BxdServlet")
    @ResponseBody
    ResponseData bxdServlet(@RequestParam("op")String op, @RequestParam(value = "eid",required = false)String eid,
                            @RequestParam(value = "xh",required = false)String xh, @RequestParam(value = "xxdd",required = false)String xxdd,
                            @RequestParam(value = "yysj",required = false)String yysj,
                            @RequestParam(value = "bxlb",required = false)String bxlb, @RequestParam(value = "bxnr",required = false)String bxnr,
                            @RequestParam(value = "sbrsj",required = false)String sbrsj, @RequestParam(value = "sbrxh",required = false)String sbrxh,
                            @RequestParam(value = "sbr",required = false)String sbr, @RequestParam(value = "tp",required = false)String tp,
                            @RequestParam(value = "cxsy",required = false)String cxsy, @RequestParam(value = "pj",required = false)String pj,
                            @RequestParam(value = "pjnr",required = false)String pjnr, @RequestParam(value = "pjzj",required = false)String pjzj,
                            @RequestParam(value = "bid",required = false)String bid) throws IOException {
        if(StringUtils.isWhitespace(op) || StringUtils.isEmpty(op) || StringUtils.isBlank(op))
            return new ResponseData("2");
        switch (op){
            case "sbrbxd" : return sbr(eid, xh);
            case "upbxdbysbr" : return upbxdbysbr(cxsy, pj, pjnr, pjzj, xh, bid);
            case "newbxdbysbr" : return filebase64(eid, xxdd, yysj, bxlb, bxnr, sbrsj, sbrxh, sbr, tp);
            case "selqybysbr" : return selqybysbr(eid);
            case "selbxdforeid" : return selbxdforeid(eid);
            default: return new ResponseData(false);
        }
    }

    @ResponseBody
    private ResponseData selbxdforeid(String eid){
        Map<String,Object> map = new HashMap<>();
        List<Bxd> blist = bs.selbxdforeid(Integer.parseInt(eid));
        map.put("blist",blist);
        return new ResponseData(map);
    }

    @ResponseBody
    private ResponseData selqybysbr(String eid){
        Map<String,Object> map = new HashMap<>();
        Ewm e = es.selqybysbr(Integer.parseInt(eid));
        map.put("ewm",e);
        return new ResponseData(map);
    }

    @ResponseBody
    private ResponseData filebase64(String eid, String xxdd, String yysj, String bxlb, String bxnr, String sbrsj, String sbrxh, String sbr, String tp) throws IOException {
        Bxd bxd = new Bxd();
        String filename = "";
        if(tp!=null&&tp.length()!=0){
            tp = "{\"tp\":"+ tp + "}";
            JSONObject jsontp = JSONObject.fromObject(tp);
            JSONArray array = jsontp.getJSONArray("tp");
            int j = array.size();
            for(int k = 0;k<j;k++){
                JSONObject temp = array.getJSONObject(k);

                String base64 = temp.getString("base64");
                String[] ss = base64.split("\\,");
                String img64 = ss[1];

                Base64 decoder = new Base64();
                byte[] b = decoder.decode(img64);
                for(int i=0;i<b.length;++i)
                {
                    if(b[i]<0)
                    {
                        b[i]+=256;
                    }
                }

                Date d = new Date();
                String time = String.valueOf(d.getTime())+k;
                String fname = PATH_FOLDER+"/"+time+"."+temp.getString("hz");
                filename = filename + "|" + time+"."+temp.getString("hz");
                OutputStream out = new FileOutputStream(fname);
                out.write(b);
                out.flush();
                out.close();
            }
            bxd.setTp(filename);
        }
        bxd.setEid(Integer.parseInt(eid));
        bxd.setXxdd(xxdd);
        bxd.setYysj(yysj);
        bxd.setBxlb(bxlb);
        bxd.setBxnr(bxnr);
        bxd.setSbr(sbr);
        bxd.setSbrsj(sbrsj);
        bxd.setSbrxh(sbrxh);

        return bs.newbxdbysbr(bxd) == 1
                ? new ResponseData(true)
                    : new ResponseData(false);

    }


    @ResponseBody
    private ResponseData upbxdbysbr(String cxsy, String pj, String pjnr, String pjzj, String xh, String bid){
        Bxd b = new Bxd();
        b.setSbrxh(xh);
        b.setId(Integer.parseInt(bid));
        b.setPj(pj);
        b.setPjnr(pjnr);
        b.setPjzj(pjzj);
        b.setCxsy(cxsy);
        bs.upbxdbysbr(b);
        return new ResponseData(true);
    }

    @ResponseBody
    private ResponseData sbr(String eid, String xh){
        Map<String,Object> map = new HashMap<>();
        Bxd b = new Bxd();
        b.setSbrxh(xh);
        if(eid!=null){
            b.setEid(Integer.parseInt(eid));
        }
        map.put("blist",bs.selforsbr(b));
        return new ResponseData(map);
    }

}
