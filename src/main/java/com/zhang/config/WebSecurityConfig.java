package com.zhang.config;

import com.zhang.handler.JwtAuthenticationTokenFilter;
import com.zhang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author zhang
 * &#064;date  2024/2/10
 * &#064;Description  配置类
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
                    httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(myAuthenticationEntryPoint);
                }  )

                .csrf(AbstractHttpConfigurer::disable)//关闭csrf防御
                .securityMatcher("/user/**")
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        //login免认证
                        /*.requestMatchers(HttpMethod.POST,"/user/login").permitAll()*/
                        .anyRequest()
                        .authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                /*.formLogin(Customizer.withDefaults());*/
                .formLogin(form -> form
                        .loginPage("/user/login")
                        .permitAll());
        //把token校验过滤器添加到过滤器链中
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    /**
     * 发布一个 AuthenticationEventPublisher @Bean，用于发布认证事件
     * @param delegate
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AuthenticationEventPublisher.class)
    public DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher(ApplicationEventPublisher delegate) {
        return new DefaultAuthenticationEventPublisher(delegate);
    }

    /**
     * 认证
     * @param userDetailsService
     * @param passwordEncoder
     * @return
     */
    @Bean
    public AuthenticationManager authenticationManager(
            UserService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService((UserDetailsService) userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }

    /**
     * 忽视login
     * @return
     */
    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/user/login", "/ignore2");
    }


}
