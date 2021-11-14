package com.example.demo.config;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    /**
     * 添加限流拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * 每秒钟100个令牌
         */
        registry.addInterceptor(new RateLimiterInterceptor(RateLimiter.create(100, 1, TimeUnit.SECONDS)))
                .addPathPatterns("/getTemperature");
    }

}
