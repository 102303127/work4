package com.zhang.handler;

import com.zhang.service.VideoService;

import com.zhang.utils.BeanUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@EnableAspectJAutoProxy
@Slf4j
public class Interceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //注入失败用了BeanUtils
        log.info("用户访问了视频资源");
        VideoService videoService = BeanUtils.getBean(VideoService.class);
        String pathInfo = request.getServletPath();
        String videoUrl= "http://localhost:8080" + pathInfo ;
        //找到该视频增加点击量
        videoService.addVisitCount(videoUrl);
    }
}
