package com.zhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhang.pojo.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Transactional
public interface UserService extends IService<User> {

    boolean register(String username,String password);
    Map<String, String> login(String username, String password);
    User getCurrentUser(String token);
    boolean update(User user);
    UserDetails loadUserByUsername(String username);
}
