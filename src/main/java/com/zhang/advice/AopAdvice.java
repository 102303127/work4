package com.zhang.advice;

import com.zhang.service.VideoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhang
 * @date 2024/3/4
 * @Description 本来用Aop实现对视频访问切入让后增加VisitCount，但是一直不成功，就用了拦截器实现
 * 看来面向切面AOP和Spring Boot的整合还待提升
 */

@Component
@Aspect
public class AopAdvice extends HttpServlet {

    @Value("${image.urlPath}")
    private String urlPrefix;

    @Autowired
    private VideoService videoService;

    @After("within(com.zhang.handler.Interceptor))")
    public void addVisitCount(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String pathInfo = request.getServletPath();
        String videoUrl= urlPrefix + pathInfo ;
        //找到该视频增加点击量
        videoService.addVisitCount(videoUrl);
        System.out.println(pathInfo);
    }
}
