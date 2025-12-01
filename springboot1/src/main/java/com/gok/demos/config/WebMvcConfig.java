package com.gok.demos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置外部文件夹映射
        registry.addResourceHandler("/实训图片文件夹/**")
                .addResourceLocations("file:E:/暑期实训代码/实训图片文件夹/");
    }
} 