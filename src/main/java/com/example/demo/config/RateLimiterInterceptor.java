package com.example.demo.config;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * 限流拦截器
 */
@Slf4j
public class RateLimiterInterceptor implements HandlerInterceptor {

    private final RateLimiter rateLimiter;

    /**
     * 构造函数初始化
     */
    public RateLimiterInterceptor(RateLimiter rateLimiter) {
        super();
        this.rateLimiter = rateLimiter;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (this.rateLimiter.tryAcquire()) {
            /**
             * 成功获取到令牌
             */
            log.info("成功获取到令牌!");
            return true;
        }
        /**
         * 获取失败,直接响应错误信息
         */
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.getWriter().write("服务器繁忙,请稍后再试!");
        return false;
    }

}
