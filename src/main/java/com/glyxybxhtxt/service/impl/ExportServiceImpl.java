package com.glyxybxhtxt.service.impl;

import com.glyxybxhtxt.dao.*;
import com.glyxybxhtxt.dataObject.*;
import com.glyxybxhtxt.service.ExportService;
import com.glyxybxhtxt.util.EwmUtil;
import com.glyxybxhtxt.util.ParseUtil;
import com.google.zxing.WriterException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author lrt
 * @Date 2021/1/7 19:33
 * @Version 1.0
 * describe：将数据以xls文件形式导出
 **/
@Service
public class ExportServiceImpl implements ExportService {
    /**
     * 解析数字成为中文
     */
    @Resource
    private ParseUtil parseUtil;
    /**
     * 报修单 Mapper
     */
    @Resource
    private BxdMapper bxdMapper;
    /**
     * 接单人 Mapper
     */
    @Resource
    private JdrMapper jdrMapper;
    /**
     * 二维码 Mapper
     */
    @Resource
    private EwmMapper ewmMapper;
    /**
     * 报修区域 Mapper
     */
    @Resource
    private BxqyMapper bxqyMapper;
    /**
     * 耗材 Mapper
     */
    @Resource
    private HcMapper hcMapper;
    /**
     * 审核员 Mapper
     */
    @Resource
    private ShyMapper shyMapper;
    /**
     * 表格数据地址
     * 第一个为文件模板
     * 第二个才是导出的文件
     */
    private static String filepath = "src/main/resources/static/repairProof.xls";
    private static String exportpath = "src/main/resources/static/export.xls";
    @Override
    public void exportXlsx(String id) {
        //保留两位小数
        DecimalFormat df = new DecimalFormat("#.00");
        //耗材填写开始行
        int hcRow = 11;
        OutputStream out = null;
        InputStream file;
        // 耗材价格
        double hcPrice = 0.0;
        // 根据报修单id，查询该报修单的全部数据
        // 注意，下面基本都是基于报修单表的外键字段，获取其他表数据
        Bxd bxd = bxdMapper.selectByPrimaryKey(Integer.parseInt(id));
        // 根据接单人易班id，查询该接单人的全部数据
        Jdr jdr = jdrMapper.selectByPrimaryKey(bxd.getJid());
        // 根据二维码id，查询该二维码的全部数据
        Ewm ewm = ewmMapper.selqyid(bxd.getEid());
        // 根据二维码所在地id，查询该报修区域的全部数据。其实二维码所在地id就是一个报修区域
        Bxqy bxqy = bxqyMapper.selectByPrimaryKey(ewm.getQid());
        // 根据审核人1 id，查询该审核人1的全部数据
        Shy shy1 = shyMapper.selectByPrimaryKey(bxd.getShy1());
        // 根据审核人2 id，查询该审核人2的全部数据
        Shy shy2 = shyMapper.selectByPrimaryKey(bxd.getShy2());
        List<String> hcList;
        String hc = bxd.getHc();
        // 一个例子：原来hc：228-1|252-1|返工耗材:388-1|383-1，去掉返工耗材:后为：228-1|252-1|388-1|383-1
        hc = hc.replace("返工耗材:","");
        // hcList存有 228-1，252-1，388-1，383-1
        hcList = Arrays.asList(hc.split("\\|"));
        String emptyStar = "☆";
        String fullStar = "★";
        // 获取申报时间
        Date sbsj = bxd.getSbsj();
        // 日期时间的格式化对象， 将日期格式化为如：2021年10月3日 21:09:14
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        // 评价星级缓存区
        StringBuilder pjStar = new StringBuilder();
        // 获取评价内容
        String pjnr = bxd.getPjnr();
        // 获取追加的评价内容
        if (bxd.getPjzj() != null&& !bxd.getPjzj().equals("")){
            pjnr += " 追加:" + bxd.getPjzj();
        }
        // 评价星级
        int pj = Integer.parseInt(bxd.getPj());
        try {
            file = new FileInputStream(filepath);
            HSSFWorkbook wb = new HSSFWorkbook(file);
            HSSFSheet sheet = wb.getSheetAt(0);

            // 对二维码字节流处理
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            BufferedImage image = EwmUtil.generateQRCodeImage("https://yiban.glmc.edu.cn/bxqt/#/declare-details/"+id,125,125);
            assert image != null;
            ImageIO.write(image,"PNG",byteArrayOut);
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            HSSFClientAnchor anchor = new HSSFClientAnchor(15, 15, 0, 0,
                    (short) 8, 0, (short) 10, 4);
            patriarch.createPicture(anchor,wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_PNG));

            // 评星处理
            for (int i = 0; i < 5; i++){
                if (pj >= 1){
                    pjStar.append(fullStar);
                }else {
                    pjStar.append(emptyStar);
                }
                pj = pj - 1;
            }
            HSSFCellStyle style = sheet.getRow(1).getCell(1).getCellStyle();
            // 建议 对着xls文件看更好理解
            sheet.getRow(1).getCell(1).setCellValue(bxd.getId());
            sheet.getRow(1).getCell(3).setCellValue(bxqy.getXq());
            sheet.getRow(1).getCell(5).setCellValue(bxqy.getQy());
            sheet.getRow(1).getCell(7).setCellValue(dateFormat.format(sbsj));
            sheet.getRow(2).getCell(1).setCellValue(bxd.getXxdd());
            sheet.getRow(3).getCell(1).setCellValue(bxd.getSbr());
            sheet.getRow(3).getCell(3).setCellValue(bxd.getSbrsj());
            sheet.getRow(3).getCell(6).setCellValue(bxd.getSbrxh());
            sheet.getRow(4).getCell(1).setCellValue(parseUtil.paraseBxlb(bxd.getBxlb()));
            sheet.getRow(4).getCell(3).setCellValue(bxd.getBxnr());
            sheet.getRow(5).getCell(1).setCellValue(jdr.getXm());
            sheet.getRow(5).getCell(3).setCellValue(jdr.getGh());
            sheet.getRow(5).getCell(5).setCellValue("桂林医学院后勤处");
            sheet.getRow(6).getCell(1).setCellValue(jdr.getSj());
            sheet.getRow(6).getCell(3).setCellValue(dateFormat.format(bxd.getJdsj())+" 至 "+dateFormat.format(bxd.getWxsj()));
            sheet.getRow(7).getCell(1).setCellValue(String.valueOf(pjStar));
            sheet.getRow(8).getCell(1).setCellValue(pjnr);
            int xuhao = 1;
            for (String item : hcList){
                HSSFRow row;
                if (hcRow != 11){
                    // 创建一行
                    sheet.shiftRows(hcRow, sheet.getLastRowNum(), 1,true,false);
                    row = sheet.createRow(hcRow);
                    copyRows(11,11,hcRow,sheet);
                }else {
                    row = sheet.getRow(hcRow);
                }

                //序号
                row.createCell(0).setCellValue(xuhao);
                Hc thc = hcMapper.selectByPrimaryKey(Integer.parseInt(item.split("-")[0]));
                //填写名称
                row.createCell(1).setCellValue(thc.getMc());
                //填写价格
                String jg = df.format(thc.getJg());
                row.createCell(6).setCellValue(jg);
                //填写单位
                row.createCell(7).setCellValue(thc.getDw());
                //填写数量
                row.createCell(8).setCellValue(item.split("-")[1]);
                //填写小计
                row.createCell(9).setCellValue(df.format(thc.getJg()*Double.parseDouble(item.split("-")[1])));
//                hcPrice += thc.getJg()*Double.parseDouble(item.split("-")[1]);
                hcPrice += Double.parseDouble(jg)*Double.parseDouble(item.split("-")[1]);

                row.iterator().forEachRemaining(cell -> {
                    cell.setCellStyle(style);
                });
                hcRow++;
                xuhao++;
            }
            sheet.getRow(hcRow).getCell(1).setCellValue(bxd.getGs());
            sheet.getRow(hcRow).getCell(3).setCellValue(Double.parseDouble(bxd.getGs())*31.25);
            double sum = hcPrice + Double.parseDouble(bxd.getGs())*31.25;
            sheet.getRow(hcRow).getCell(8).setCellValue(df.format(sum));
            //转下一行
            hcRow++;
            sheet.getRow(hcRow).getCell(1).setCellValue(shy1.getXm());
            sheet.getRow(hcRow).getCell(3).setCellValue(shy2.getXm());
            sheet.getRow(hcRow).getCell(5).setCellValue(shy1.getXm());
            if (sum >= 500){
                sheet.getRow(hcRow).getCell(9).setCellValue("黄海鹏");
            }
            out = new FileOutputStream(exportpath);
            wb.write(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        } finally {
            if (out != null){
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static int getMergedRegionIndex(HSSFSheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();

        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return i;
                }
            }
        }

        return 0;
    }

    public static void copyRows(int startRow,int endRow,int pPosition,HSSFSheet sheet){
        int pStartRow=startRow;
        int pEndRow=endRow;
        int targetRowFrom;
        int targetRowTo;
        int columnCount;
        CellRangeAddress region=null;
        int i;
        int j;
        if(pStartRow == -1 || pEndRow == -1) {
            return;
        }
        // 拷贝合并的单元格
        for(i=0;i<sheet.getNumMergedRegions();i++){
            region=sheet.getMergedRegion(i);
            if((region.getFirstRow() >= pStartRow) && (region.getLastRow() <= pEndRow)) {
                targetRowFrom=region.getFirstRow()-pStartRow+pPosition;
                targetRowTo=region.getLastRow()-pStartRow+pPosition;
                CellRangeAddress newRegion=region.copy();
                newRegion.setFirstRow(targetRowFrom);
                newRegion.setFirstColumn(region.getFirstColumn());
                newRegion.setLastRow(targetRowTo);
                newRegion.setLastColumn(region.getLastColumn());
                sheet.addMergedRegion(newRegion);
            }
        }
        // 设置列宽
        for(i=pStartRow;i<=pEndRow;i++){
            HSSFRow sourceRow=sheet.getRow(i);
            columnCount=sourceRow.getLastCellNum();
            if(sourceRow != null){
                HSSFRow newRow=sheet.createRow(pPosition - pStartRow + i);
                newRow.setHeight(sourceRow.getHeight());
                for(j=0;j<columnCount;j++){
                    HSSFCell templateCell=sourceRow.getCell(j);
                    if(templateCell != null){
                        HSSFCell newCell=newRow.createCell(j);
                        copyCell(templateCell,newCell);
                    }
                }
            }
        }
    }

    /**
     * 复制单元格
     * @param srcCell 原始单元格
     * @param distCell 目标单元格
     */
    public static void copyCell(HSSFCell srcCell,HSSFCell distCell){
        distCell.setCellStyle(srcCell.getCellStyle());
        if(srcCell.getCellComment() != null){
            distCell.setCellComment(srcCell.getCellComment());
        }
        int srcCellType=srcCell.getCellType();
        distCell.setCellType(srcCellType);
        if(srcCellType==HSSFCell.CELL_TYPE_NUMERIC){
            if(HSSFDateUtil.isCellDateFormatted(srcCell)){
                distCell.setCellValue(srcCell.getDateCellValue());
            }
            else{
                distCell.setCellValue(srcCell.getNumericCellValue());
            }
        }
        else if(srcCellType==HSSFCell.CELL_TYPE_STRING){
            distCell.setCellValue(srcCell.getRichStringCellValue());
        }
        else if(srcCellType==HSSFCell.CELL_TYPE_BLANK){
            // nothing21
        }
        else if(srcCellType==HSSFCell.CELL_TYPE_BOOLEAN){
            distCell.setCellValue(srcCell.getBooleanCellValue());
        }
        else if(srcCellType==HSSFCell.CELL_TYPE_ERROR){
            distCell.setCellErrorValue(srcCell.getErrorCellValue());
        }
        else if(srcCellType==HSSFCell.CELL_TYPE_FORMULA){
            distCell.setCellFormula(srcCell.getCellFormula());
        }
        else{ // nothing29

        }
    }
}
