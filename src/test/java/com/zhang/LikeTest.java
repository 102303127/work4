package com.zhang;

import com.zhang.mapper.UserLikeMapper;
import com.zhang.pojo.UserLike;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class LikeTest {

    @Autowired
    private UserLikeMapper userLikeMapper;
    @Test
    public void test(){
        UserLike userLike = new UserLike();
        userLike.setUserId(String.valueOf(1751791889439989761L));
        userLike.setVideoId(String.valueOf(1752881175266529282L));
        userLikeMapper.insert(userLike);
        /*userLikeMapper.deleteById(1754024359010934786L);*/
    }
}
