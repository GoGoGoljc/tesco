package com.legou.comment.config;

import com.legou.comment.filter.CheckParamsInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author SJXY
 */
public class WebMvcConfig implements WebMvcConfigurer {
    CheckParamsInterceptor checkSourceInterceptor = new CheckParamsInterceptor();
    //增加校验拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 这个地方可以定义拦截器的具体的路径
        registry.addInterceptor(checkSourceInterceptor).addPathPatterns("/**");
    }

}
