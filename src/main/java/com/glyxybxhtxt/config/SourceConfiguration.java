package com.glyxybxhtxt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

/**
 * @Author lrt
 * @Date 2021/6/2 18:16
 * @Version 1.0
 * 访问路径配置，保存路径配置不在这里，在PathUtil
 **/
@Configuration
public class SourceConfiguration extends WebMvcConfigurationSupport {
    @Resource
    private PathConfig pathConfig;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        String url = pathConfig.getUploadpath();
        registry.addResourceHandler("/bxdimg/**").addResourceLocations("file:"+url);
        super.addResourceHandlers(registry);

    }

}
