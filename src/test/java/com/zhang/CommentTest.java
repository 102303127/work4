package com.zhang;

import com.zhang.mapper.CommentMapper;
import com.zhang.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommentTest {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentService commentService;
    @Test
    public void test(){
        commentService.delete("1752881175266529282","0","1753573952463765506");

    }
}
