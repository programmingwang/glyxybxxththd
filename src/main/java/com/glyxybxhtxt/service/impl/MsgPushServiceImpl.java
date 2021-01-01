package com.glyxybxhtxt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glyxybxhtxt.service.MsgPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @Author lrt
 * @Date 2020/12/30 22:54
 * @Version 1.0
 **/
@Service
public class MsgPushServiceImpl implements MsgPushService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public boolean msgpush(String to_ybid, String content) {
        String uri = "https://openapi.yiban.cn/oauth/reset_token";
        MultiValueMap<String,String> jsonMap = new LinkedMultiValueMap<>();
        jsonMap.add("client_id","3fe1ac4f66fe7bbc");
        jsonMap.add("client_secret","3ba03e09d703867644388a54e90f4c6a");
        jsonMap.add("dev_uid","31446288");
        ResponseEntity<String> apiResponse = restTemplate.postForEntity
                (
                        uri,
                        generatePostJson(jsonMap),
                        String.class
                );
        JSONObject result = JSON.parseObject(apiResponse.getBody());
        String status = (String)result.get("status");
        if (!status.equals("200")){
            System.out.println("授权失败");
            return false;
        }
        String access_token = (String)result.get("access_token");
        uri = "https://openapi.yiban.cn/msg/letter";
        jsonMap.clear();
        jsonMap.add("access_token",access_token);
        jsonMap.add("to_yb_uid",to_ybid);
        jsonMap.add("content",content);
        jsonMap.add("template","system");
        apiResponse = restTemplate.postForEntity(
                uri,
                generatePostJson(jsonMap),
                String.class
        );
        result = JSON.parseObject(apiResponse.getBody());
        return result.get("status").equals("success");
    }

    public HttpEntity<MultiValueMap<String, String>> generatePostJson(MultiValueMap<String, String> jsonMap) {

        //如果需要其它的请求头信息、都可以在这里追加
        HttpHeaders httpHeaders = new HttpHeaders();

        MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded");

        httpHeaders.setContentType(type);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(jsonMap, httpHeaders);

        return httpEntity;
    }

}
