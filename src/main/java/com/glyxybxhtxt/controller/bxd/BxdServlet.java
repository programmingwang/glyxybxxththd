package com.glyxybxhtxt.controller.bxd;

import com.glyxybxhtxt.dataObject.Bxd;
import com.glyxybxhtxt.dataObject.Ewm;
import com.glyxybxhtxt.dataObject.Shy;
import com.glyxybxhtxt.response.ResponseData;
import com.glyxybxhtxt.service.*;
import com.glyxybxhtxt.util.ParseUtil;
import com.glyxybxhtxt.util.PathUtil;
import com.glyxybxhtxt.util.AutoOrder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Author:wangzh
 * Date: 2020/12/4 14:54
 * Version: 1.0
 */
@RestController

public class BxdServlet {

    private static final long serialVersionUID = 1L;
    private static String PATH_FOLDER = PathUtil.getUploadPath();
    @Autowired
    private BxdService bs;
    @Autowired
    private EwmService es;
    @Resource
    private AutoOrder zdpd;
    @Resource
    private ShyService ss;
    @Resource
    private QdbService qs;
    @Resource
    private ParseUtil parse;
    @Autowired
    private MsgPushService ybmsg;


//    @Override
//    public void init(ServletConfig config) throws ServletException {
//        ServletContext servletCtx = config.getServletContext();
//        PATH_FOLDER = servletCtx.getRealPath("/bxdimg");
//    }

    @RequestMapping("/BxdServlet")
    @ResponseBody
    ResponseData bxdServlet(@RequestParam("op") String op, @RequestParam(value = "eid", required = false) String eid,
                            @RequestParam(value = "xh", required = false) String xh, @RequestParam(value = "xxdd", required = false) String xxdd,
                            @RequestParam(value = "yysj", required = false) String yysj,
                            @RequestParam(value = "bxlb", required = false) String bxlb, @RequestParam(value = "bxnr", required = false) String bxnr,
                            @RequestParam(value = "sbrsj", required = false) String sbrsj, @RequestParam(value = "sbrxh", required = false) String sbrxh,
                            @RequestParam(value = "sbr", required = false) String sbr, @RequestParam(value = "tp", required = false) String tp,
                            @RequestParam(value = "sp", required = false) String sp,
                            @RequestParam(value = "cxsy", required = false) String cxsy, @RequestParam(value = "pj", required = false) String pj,
                            @RequestParam(value = "pjnr", required = false) String pjnr, @RequestParam(value = "pjzj", required = false) String pjzj,
                            @RequestParam(value = "bid", required = false) String bid, @RequestParam(value = "jid", required = false) String jid,
                            @RequestParam(value = "hc", required = false) String hc) throws IOException {
        if (StringUtils.isWhitespace(op) || StringUtils.isEmpty(op) || StringUtils.isBlank(op))
            return new ResponseData("2");
        switch (op) {
            case "sbrbxd":
                return sbr(eid, xh);
            case "upbxdbysbr":
                return upbxdbysbr(cxsy, pj, pjnr, pjzj, xh, bid);
            case "newbxdbysbr":
                return filebase64(eid, xxdd, yysj, bxlb, bxnr, sbrsj, sbrxh, sbr, tp, sp, jid, bid, hc);
            case "selqybysbr":
                return selqybysbr(eid);
            case "selbxdforeid":
                return selbxdforeid(eid);
            default:
                return new ResponseData(false);
        }
    }

    @ResponseBody
    private ResponseData selbxdforeid(String eid) {
        Map<String, Object> map = new HashMap<>();
        List<Bxd> blist = bs.selbxdforeid(Integer.parseInt(eid));
        for (Bxd bxd : blist) {
            bxd.setBxlb(parse.paraseBxlb(bxd.getBxlb()));
            bxd.setHc(parse.paraseHc(bxd.getHc()));
        }
        map.put("blist", blist);
        return new ResponseData(map);
    }

    @ResponseBody
    private ResponseData selqybysbr(String eid) {
        Map<String, Object> map = new HashMap<>();
        Ewm e = es.selqybysbr(Integer.parseInt(eid));
        map.put("ewm", e);
        return new ResponseData(map);
    }

    @ResponseBody
    //整合返工和新增工单，返工要传jdr的jid和本单的id
    private ResponseData filebase64(String eid, String xxdd, String yysj, String bxlb, String bxnr, String sbrsj, String sbrxh, String sbr, String tp, String sp, String jid, String bid, String hc) throws IOException {
        Bxd bxd = new Bxd();
        String filename = "";
        if (tp != null && tp.length() != 0) {
            tp = "{\"tp\":" + tp + "}";
            JSONObject jsontp = JSONObject.fromObject(tp);
            JSONArray array = jsontp.getJSONArray("tp");
            int j = array.size();
            for (int k = 0; k < j; k++) {
                JSONObject temp = array.getJSONObject(k);

                String base64 = temp.getString("base64");
                String[] ss = base64.split("\\,");
                String img64 = ss[1];

                Base64 decoder = new Base64();
                byte[] b = decoder.decode(img64);
                for (int i = 0; i < b.length; ++i) {
                    if (b[i] < 0) {
                        b[i] += 256;
                    }
                }

                Date d = new Date();
                String time = String.valueOf(d.getTime()) + k;
                String fname = PATH_FOLDER + "/" + time + "." + temp.getString("hz");
                filename = filename + "|" + time + "." + temp.getString("hz");
                OutputStream out = new FileOutputStream(fname);
                out.write(b);
                out.flush();
                out.close();
            }
            bxd.setTp(filename);
        }
        //eid 二维码的id
        bxd.setEid(Integer.parseInt(eid));
        //详细地点
        bxd.setXxdd(xxdd);
        bxd.setSp(sp);
        //预约时间
        bxd.setYysj(yysj);
        //报修类别
        bxd.setBxlb(bxlb);
        bxd.setBxnr(bxnr);
        bxd.setSbr(sbr);
        bxd.setSbrsj(sbrsj);
        bxd.setSbrxh(sbrxh);
        //如果是返工，就不自动派单，否则则自动派单
        if(StringUtils.isAllBlank(jid, bid)){
            //自动分配审核员
//            -------------------------------------------------------------------------
            //1、先查出所有符合当前校区的审核员
            List<Shy> optimalShy = ss.selOptimalShy(Integer.parseInt(eid));
            //做筛选，防止重复打卡而引发的只有一个合适的审核员
            List<Shy> collect = optimalShy.stream().distinct().collect(Collectors.toList());
            bxd.setShy1(collect.get(0).getYbid());
            bxd.setShy2(collect.get(1).getYbid());
            String zdpdResult = zdpd.zdpd(eid, bxlb);
            if(StringUtils.startsWith(zdpdResult, "6U@U6WX2^&nb6YIILV")){
                bxd.setState(0);
                bs.newbxdbysbr(bxd);
                return new ResponseData(StringUtils.substringAfter(zdpdResult, "6U@U6WX2^&nb6YIILV"));
            }
            bxd.setState(1);
            bxd.setJid(zdpdResult);

            return bs.newbxdbysbr(bxd) == 1
                    ? new ResponseData(true)
                    : new ResponseData(false);
        }else{
            //返工
            Bxd fgbxd = new Bxd();
            fgbxd.setId(Integer.parseInt(bid));
            fgbxd.setTp(filename);
            fgbxd.setSp(sp);
            //预约时间
            fgbxd.setYysj(yysj);
            //返工天数重算
            fgbxd.setFgts(15);
            //新的报修内容
            fgbxd.setBxnr(bxnr);
//            //申报时间重算
//            fgbxd.setSbsj(new Date());
            //将单状态改为1
            fgbxd.setState(1);
            //工时清零
            fgbxd.setGs("");
            fgbxd.setShy1state(0);
            fgbxd.setShy2state(0);
            bs.fg(fgbxd);
            ybmsg.msgpush(jid,"您有报修单需要返工，请及时处理！详细地点："+es.selxxwz(Integer.parseInt(eid)));
            return new ResponseData(true);
        }
    }


    @ResponseBody
    private ResponseData upbxdbysbr(String cxsy, String pj, String pjnr, String pjzj, String xh, String bid) {
        Bxd b = new Bxd();
        b.setSbrxh(xh);
        b.setId(Integer.parseInt(bid));
        b.setPj(pj);
        b.setPjnr(pjnr);
        b.setPjzj(pjzj);
        b.setCxsy(cxsy);
        bs.upbxdbysbr(b);
        if(cxsy != null){
            Bxd currentBxd = bs.selonebxd(Integer.parseInt(bid));
            if(currentBxd.getJid() != null) ybmsg.msgpush(currentBxd.getJid(),"二维码地址：http://yiban.glmc.edu.cn/bx?eid="+currentBxd.getEid()+"\n您在"+es.selxxwz(currentBxd.getEid())+"申报人已经撤销了，请注意！");
        }
        return new ResponseData(true);
    }

    @ResponseBody
    private ResponseData sbr(String eid, String xh) {
        Map<String, Object> map = new HashMap<>();
        Bxd b = new Bxd();
        b.setSbrxh(xh);
        if (eid != null) {
            b.setEid(Integer.parseInt(eid));
        }
        List<Bxd> blist = bs.selforsbr(b);
        for (Bxd bxd : blist) {
            bxd.setBxlb(parse.paraseBxlb(bxd.getBxlb()));
            bxd.setHc(parse.paraseHc(bxd.getHc()));
        }
        map.put("blist",blist);
        return new ResponseData(map);
    }

}


