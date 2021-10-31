package com.glyxybxhtxt.exception;

import com.glyxybxhtxt.response.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;


/**
 * Author:wangzh
 * Date: 2020/11/1 14:54
 * Version: 1.0
 */
// 指定：只有使用了RestController的类才会使用这些异常处理方法
@ControllerAdvice(annotations = {RestController.class})
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理其他异常
     * @param e
     * @return
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public ResponseData exceptionHandler(Exception e){
        logger.error("未知异常！原因是:",e);
        return new ResponseData("1");
    }

 
    /**
     * 处理转换异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = ParseException.class)
    @ResponseBody
    public ResponseData parseExceptionHandler(ParseException e){
        logger.error("类型转换异常！原因是:",e);
        return new ResponseData("日期格式出错了！");
    }


}