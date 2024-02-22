package com.zhang;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.mapper.VideoMapper;
import com.zhang.pojo.Video;
import com.zhang.service.VideoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VideoTest {

    @Autowired
    private VideoService videoService;

    @Autowired
    VideoMapper videoMapper;

    @Test
    public void Test(){

        /*System.out.println(videoService.getTotal(1751791889439989761L));*/

        /*Page<Video> page = new Page<>(1,1);
        videoMapper.selectPage(page,null);
        System.out.println(page.getRecords());*/
    }
}
