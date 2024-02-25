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

        System.out.println(JwtUtils.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJlZDA5MjJlOTBiOTU0OGNkOWYxY2M0NWM3MzIyZGFiNSIsInN1YiI6IjE3NTgyODQ5MDExNDE2MzUwNzQiLCJpc3MiOiJzZyIsImlhdCI6MTcwODc3NDkwMSwiZXhwIjoxNzA4Nzc1NTAxfQ.3KOFL6oyHyBh2fU4YFpW_eKhNE_Zm2XEnBMImd8po9w").getExpiration());
        System.out.println(JwtUtils.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJlZDA5MjJlOTBiOTU0OGNkOWYxY2M0NWM3MzIyZGFiNSIsInN1YiI6IjE3NTgyODQ5MDExNDE2MzUwNzQiLCJpc3MiOiJzZyIsImlhdCI6MTcwODc3NDkwMSwiZXhwIjoxNzA4Nzc1NTAxfQ.3KOFL6oyHyBh2fU4YFpW_eKhNE_Zm2XEnBMImd8po9w").getIssuedAt());
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
