package com.hu.video.config;

import com.hu.video.interceptor.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

@Configuration
public class WebConfig  implements WebMvcConfigurer {

    @Resource
    private LogInterceptor logInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor).addPathPatterns("/**").excludePathPatterns("/login/**","/video/**");

//        registry.addInterceptor(new OldLoginInterceptor()).addPathPatterns("/admin/oldLogin");

//        registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/admin/*").excludePathPatterns("/admin/oldLogin");
    }

    // 需要拦截的资源目录
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        WebMvcConfigurer.super.configureMessageConverters(converters);
    }


    /*
    拦截类型
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//配置可以被跨域的路径，可以任意配置，可以具体到直接请求路径。
                .allowCredentials(true)
                .allowedHeaders("*")//允许所有的请求header访问，可以自定义设置任意请求头信息
                .allowedOriginPatterns("*")
//                .allowedOrigins("*")//允许所有的请求域名访问我们的跨域资源，可以固定单条或者多条内容，如：“http://www.aaa.com”，只有该域名可以访问我们的跨域资源。
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")//允许所有的请求方法访问该跨域资源服务器，如：POST、GET、PUT、DELETE等。
                .maxAge(3600);
        WebMvcConfigurer.super.addCorsMappings(registry);
    }
}
//    Request Header
//
//Origin：请求的源信息（协议 + 域名 + 端口）
//Access-Control-Request-Method：预检请求类型
//Access-Control-Request-Headers：额外发送的Header信息
//
//
//Resonse Header
//
//Access-Control-Allow-Origin：服务器接受的源信息，“*”表示所有
//Access-Control-Allow-Methods：服务器支持的所有跨域请求类型，“*”表示所有
//Access-Control-Allow-Credentials：服务器是否接受Cookies和HTTP Authentication
//Access-Control-Expose-Headers：服务器暴露的额外的Header，“*”表示所有
//Access-Control-Max-Age：本次预检的有效期

