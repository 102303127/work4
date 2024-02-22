package com.zhang;


import com.zhang.mapper.UserMapper;

import com.zhang.service.FollowService;
import com.zhang.service.UserService;


import com.zhang.utils.JwtUtils;
import com.zhang.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class Work40124ApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private FollowService followService;

    @Autowired
    RedisUtil redisUtil;
    @Test
    public void testConn() {
    }
    @Test
    void contextLoads() {

        System.out.println(JwtUtils.isTokenExpired("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJlN2MyMTRiZWFmYjE0MGFhODUyNGY4ZmI5MDM3MTcyNiIsInN1YiI6IjE3NTgyODQ5MDExNDE2MzUwNzQiLCJpc3MiOiJzZyIsImlhdCI6MTcwODU2Mjc4MywiZXhwIjoxNzA4NTYyNzg0fQ.RAl7SUapFiXbuiV-v3L5qQerv1ihLk1Y7bB6blB8css"));
        System.out.println(JwtUtils.isTokenExpired("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxNjkzZTgyNzdjNjA0ZTMxODA5YmE5YTc5ZjdmZjU5OSIsInN1YiI6IjE3NTgyODQ5MDExNDE2MzUwNzQiLCJpc3MiOiJzZyIsImlhdCI6MTcwODU2Mjc4NCwiZXhwIjoxNzA4NTk4Nzg0fQ.Z_mYdCQVc7rkJvpALoh0rW_BptmEtrOE0m0hNvCxh7Q"));
        //System.out.println(userService.getCurrentUser("6179a1c422454d07959da5e4356774d4"));

/*        String accessToken = JwtUtils.createAccessToken("123456");
        System.out.println(JwtUtils.parseJWT(accessToken).getIssuedAt());*/

        /*System.out.println(userMapper.GetById(1751791889439989761L));*/

        /*System.out.println(redisUtil.get("d489335c8f2b4a4897f5ca8c81a15e0f"));*/
        /*followService.list(String.valueOf(1751791889439989761L),1,1);*/
        /*System.out.println("hello");
        redisTemplate.opsForValue().set("ke","ni");
        System.out.println(redisTemplate.opsForValue().get("ke"));*/

       /* String token = userService.login("小小世界", "123456");
        User user=userMapper.selectByName("小小世界");
        redisUtil.set("token:"+token,user);

        System.out.println(token);*/

        /*User currentUser = userService.getCurrentUser("1749801650601238530:42ff4de0734a4a428a4aea249df7bc0a");
        System.out.println(currentUser);*/

        /*System.out.println(userService.getById(1749801650601238530L));*/
        /*User user = new User();
        user.setUsername("小小世界");
        user.setPassword("123456");
        Date currentDate = new Date();
        user.setCreated_at(currentDate);*/

        /*userMapper.insert(user);*/
    }
}
