package com.lpm.shop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 解决跨域问题
 * @author Master
 */
//@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //开放哪些ip，端口，域名访问权限
                .allowedOrigins("*")
                //开放哪些http请求方式
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600)
                //允许哪些请求头
                .allowedHeaders("*");
    }
}
