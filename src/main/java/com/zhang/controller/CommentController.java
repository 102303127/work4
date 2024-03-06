package com.zhang.controller;

import com.zhang.pojo.Comment;
import com.zhang.pojo.User;
import com.zhang.service.CommentService;
import com.zhang.service.UserService;
import com.zhang.utils.DataUtils;
import com.zhang.vo.result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


/**
 * @author zhang
 * &#064;date  2024/2/10
 * &#064;Description  评论实现Controller层
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/publish")
    public result publish(@RequestParam(value = "video_id",required = false)String video_id,
                          @RequestParam(value = "comment_id",required = false)String comment_id,
                          @RequestParam(value = "content",required = false) String content){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //数据校验设置值
        String videoId = DataUtils.validation(video_id);
        String commentId = DataUtils.validation(comment_id);
        //判断执行
        boolean flag = commentService.publish(user.getId(),videoId, commentId, content);
        if(flag) return result.OK();
        else return result.Fail();
    }


    @GetMapping("/list")
    public result list(@RequestParam(value = "video_id",required = false)String video_id,
                       @RequestParam(value = "comment_id",required = false)String comment_id,
                       @RequestParam("page_size") Integer page_size,
                       @RequestParam("page_num") Integer page_num){
        //数据校验设置值
        String videoId = DataUtils.validation(video_id);
        String commentId = DataUtils.validation(comment_id);
        List<Comment> list = commentService.list(videoId, commentId, page_size, page_num);
        if(list!=null) return result.OK(list);
        else return result.Fail();
    }

    @DeleteMapping("/delete")
    public result delete(@RequestParam(value = "video_id",required = false)String video_id,
                         @RequestParam(value = "comment_id",required = false)String comment_id,
                         @RequestParam("id")String Id){
        //数据校验设置值
        String videoId = DataUtils.validation(video_id);
        String commentId = DataUtils.validation(comment_id);
        String id = DataUtils.validation(Id);
        boolean flag = commentService.delete(videoId, commentId,id);
        if(flag) return result.OK();
        else return result.Fail();
    }
}
