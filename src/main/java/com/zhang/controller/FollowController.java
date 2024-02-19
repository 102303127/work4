package com.zhang.controller;

import com.zhang.pojo.FollowUser;
import com.zhang.pojo.User;
import com.zhang.service.FollowService;
import com.zhang.vo.result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhang
 * &#064;date  2024/2/10
 * &#064;Description  实现关注Controller层
 */
@RestController
@RequestMapping
public class FollowController {

    @Autowired
    private FollowService followService;

    @PostMapping("/relation/action")
    public result action(@RequestParam("to_user_id")String followId,
                         @RequestParam("action_type") Integer action){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = user.getId();
        if(action==0){
            boolean flag = followService.add(String.valueOf(userId), followId);
            if (flag) return result.OK();
            else return result.Fail();
        }
        if(action==1){
            boolean flag = followService.delete(String.valueOf(userId), followId);
            if (flag) return result.OK();
            else return result.Fail();
        }
        return result.Fail("请输入正确指示");
    }
    @GetMapping("/following/list")
    public result list(@RequestParam("user_id") String userId,
                       @RequestParam("page_size") Integer page_size,
                       @RequestParam("page_num") Integer page_num){
        List<FollowUser> list = followService.list(userId, page_size, page_num);
        Long total = followService.getTotal(userId);
        boolean flag = list!=null;
        if (flag) return result.OK(list,total);
        else return result.Fail();
    }
    @GetMapping("/follower/list")
    public result listFollow(@RequestParam("user_id") String followId,
                             @RequestParam("page_size") Integer page_size,
                             @RequestParam("page_num") Integer page_num){
        List<FollowUser> list = followService.listFollow(followId, page_size, page_num);
        Long total = followService.getTotalFollow(followId);
        boolean flag = list!=null;
        if (flag) return result.OK(list,total);
        else return result.Fail();
    }

    @GetMapping("/friends/list")
    public result listFriend(@RequestParam("page_size") Integer page_size,
                             @RequestParam("page_num") Integer page_num){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = user.getId();
        List<FollowUser> listFriend = followService.listFriend(String.valueOf(userId), page_size, page_num);
        boolean flag = listFriend!=null;
        if (flag) return result.OK(listFriend);
        else return result.Fail();
    }

}
