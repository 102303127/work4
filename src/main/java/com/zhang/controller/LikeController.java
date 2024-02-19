package com.zhang.controller;


import com.zhang.pojo.User;
import com.zhang.pojo.Video;
import com.zhang.service.UserLikeService;
import com.zhang.service.UserService;
import com.zhang.utils.DataUtils;
import com.zhang.vo.result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhang
 * &#064;date  2024/2/10
 * &#064;Description  实现点赞喜欢Controller层
 */
@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private UserLikeService userLikeService;

    @PostMapping("/action")
    public result action(@RequestParam(value = "video_id",required = false)String video_id,
                         @RequestParam(value = "comment_id",required = false)String comment_id,
                         @RequestParam("action_type") int action,
                         HttpServletRequest request){
        /*String token = request.getHeader("token");
        Long userId = userService.getCurrentUser(token).getId();*/
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //数据校验设置值
        String videoId = DataUtils.validation(video_id);
        String commentId = DataUtils.validation(comment_id);
        if(action==0) {
            boolean flag = userLikeService.add(String.valueOf(user.getId()), videoId, commentId);
             if (flag) return result.OK();
             else return result.Fail();
        }
        if (action==1) {
            boolean flag = userLikeService.delete(String.valueOf(user.getId()), videoId, commentId);
            if (flag) return result.OK();
            else return result.Fail();
        }
        return result.Fail();
    }

    @GetMapping("/list")
    public result list(@RequestParam("user_id") String userId,
                       @RequestParam("page_size") Integer page_size,
                       @RequestParam("page_num") Integer page_num){
        List<Video> list = userLikeService.list(userId, page_size, page_num);
        boolean flag=list!=null;
        if(flag) return result.OK(list);
        else return result.Fail();
    }
}
