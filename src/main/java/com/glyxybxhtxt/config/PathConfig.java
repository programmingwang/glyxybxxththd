package com.glyxybxhtxt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author lrt
 * @Date 2021/6/2 18:14
 * @Version 1.0
 * 访问路径配置，保存路径配置不在这里，在PathUtil
 **/
@Data
@Component
@ConfigurationProperties(prefix = "my.config")
public class PathConfig {
    private String uploadpath;
}
