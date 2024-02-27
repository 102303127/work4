package com.zhang.config;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component("customAuthenticationEntryPoint")
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        if (!response.isCommitted()) {
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            try (ServletOutputStream ous = response.getOutputStream()) {
                Map<String, Object> map = new HashMap<>(16);
                map.put("code", -1);
                map.put("success", false);
                map.put("message", Optional.ofNullable(request.getAttribute("errorMessage")).orElse("请先登录").toString());
                ous.write(JSON.toJSONString(map).getBytes(StandardCharsets.UTF_8));
            }
        }
    }
}
