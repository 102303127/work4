package com.zhang.config;
import com.zhang.handler.Interceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhang
 * &#064;date  2024/2/10
 * &#064;Description  配置类
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    //注入不同环境的视频存放地址
    @Value("${image.basePath}")
    private String imagePath;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Interceptor()).addPathPatterns("/image/**");
    }
    /**
     * 配置静态资源路径
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**")
                .addResourceLocations("file:"+imagePath+"\\");
    }
}
