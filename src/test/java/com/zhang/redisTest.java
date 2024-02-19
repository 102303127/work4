package com.zhang;

import com.zhang.mapper.VideoMapper;
import com.zhang.pojo.Video;
import com.zhang.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@SpringBootTest
public class redisTest {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private VideoMapper videoMapper;
    @Test
    public void test(){
        String key = "热门排行榜-video";
        List<Video> videos = videoMapper.selectList(null);
        for (Video video : videos) {
            redisUtil.addScore(key,String.valueOf(video.getId()),video.getVisitCount());
        }

        Set<Object> allMembers = redisUtil.getAllMembers(key);
        for (Object allMember : allMembers) {
            System.out.println(allMember);
        }
    }
}
