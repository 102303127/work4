package com.zhang.handler;

import com.zhang.exception.userException;
import com.zhang.pojo.User;
import com.zhang.utils.JwtUtils;
import com.zhang.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //获取accessToken
        String accessToken = request.getHeader("accessToken");

        //排除登录(login)+注册请求(register)请求，排除accessToken没有内容的
        if (!StringUtils.hasText(accessToken)
                ||Objects.equals(request.getServletPath(), "/user/login")
                ||Objects.equals(request.getServletPath(), "/user/register")) {
            //放行
            filterChain.doFilter(request, response);
            return;
        }

        //检查token时效性
        if (JwtUtils.isTokenExpired(accessToken)){
            String refreshToken = request.getHeader("refreshToken");
            if (JwtUtils.isTokenExpired(refreshToken)){
                request.setAttribute("msg","过久未登录，请重新登录");
                throw new userException("过久未登录，请重新登录");
            }else {
                //利用refreshToken生成accessToken和refreshToken
                //将新Token存到Api fox的Header中
                try {
                    accessToken = JwtUtils.createAccessTokenByRefresh(refreshToken);
                    response.setHeader("accessToken",accessToken);
                    String newRefreshToken=JwtUtils.createRefreshTokenByRefresh(refreshToken);
                    response.setHeader("refreshToken",newRefreshToken);
                } catch (Exception e) {
                    logger.error(e);
                    throw new userException("token非法");
                }
            }
        }

        //解析token
        String userid;
        try {
            Claims claims = JwtUtils.parseJWT(accessToken);
            userid = claims.getSubject();
        } catch (Exception e) {
            logger.error(e);
            throw new userException("token非法");
        }

        //从redis中获取用户信息
        String redisKey = "login:" + userid;
        User user = (User) RedisUtil.get(redisKey);
        if(Objects.isNull(user)){
            throw new userException("用户未登录");
        }

        //把用户相关信息存入SecurityContextHolder
        //TODO 获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //放行
        filterChain.doFilter(request, response);
    }
}
