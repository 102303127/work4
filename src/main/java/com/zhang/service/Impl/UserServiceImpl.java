package com.zhang.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhang.mapper.UserMapper;
import com.zhang.pojo.User;
import com.zhang.service.UserService;
import com.zhang.utils.JwtUtils;
import com.zhang.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper , User>
        implements UserService , UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        User user = userMapper.selectOne(wrapper);
        //如果查询不到数据就通过抛出异常来给出提示
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名或密码错误");
        }
        //TODO 根据用户查询权限信息 添加到LoginUser中
        //封装成UserDetails对象返回
        return user;
    }


    /**
     * 注册方法
     * @param username
     * @param password
     * @return
     */
    @Override
    public boolean register(String username,String password) {
        //密码加密
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setCreatedAt(new Date());
        return userMapper.insert(user) != 0;
    }
    /**
     * 服务层的登陆代码
     *
     * @param username
     * @param password
     * @return jwt
     */
    @Override
    public Map<String, String> login(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        // 调用authenticate方法认证时
        // 会执行UserServiceImpl中的loadUserByUsername方法
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //使用userid生成token
        User user = (User) authenticate.getPrincipal();
        String userId = user.getId().toString();
        String AccessToken = JwtUtils.createAccessToken(userId);
        String RefreshToken = JwtUtils.createRefreshToken(userId);
        //authenticate存入redis
        redisUtil.set("login:"+userId,user);
        Map<String,String> map=new HashMap<>();
        map.put("accessToken",AccessToken);
        map.put("refreshToken",RefreshToken);
        return map;
    }


    /**
     * 获取当前登录用户的方法
     *
     * @param token
     * @return
     */
    @Override
    public User getCurrentUser(String token) {
        //根据token查询当前用户
        Object user = redisUtil.get(token);
        return (User) user;
    }

    /**、
     * 更新用户的方法
     *
     * @param user
     * @return
     */
    @Override
    public boolean update(User user) {
        user.setUpdatedAt(new Date());
        return userMapper.updateById(user) != 0;
    }


}