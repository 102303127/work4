package com.zhang.controller;


import com.zhang.pojo.User;
import com.zhang.service.UserService;
import com.zhang.vo.base;
import com.zhang.vo.result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhang
 * &#064;date  2024/2/10
 * &#064;Description  用户Controller层
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

/*    @GetMapping("/user")
    public result getCurrentUser() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        return result.OK(userDetails);
    }*/

    /**
     * 注册方法
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/register")
    public result register(@RequestParam("username") String username,
                           @RequestParam("password") String password){
        boolean flag=userService.register(username,password);
        if(flag) return result.OK();
        else return result.Fail();
    }

    /**
     * 登录方法
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    public result login(@RequestParam("username") String username,
                        @RequestParam("password") String password){
        Map<String, String> map = userService.login(username, password);
        return result.OK(map);
    }


    /**
     * 查询用户操作
     *
     * @param userId
     * @return
     */
    @GetMapping("/info")
    public result getUser(@RequestParam("userId") String userId){
        User user = userService.getById(userId);
        boolean flag= user != null;
        return new result(new base(flag?10000:-1,flag ? "success":"用户查询失败，请重试"),user);
    }

}
