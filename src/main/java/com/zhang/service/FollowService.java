package com.zhang.service;

import com.zhang.pojo.Follow;
import com.zhang.pojo.FollowUser;
import com.zhang.pojo.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface FollowService {
    boolean add(String userId,String followId);
    boolean delete(String userId,String followId);
    List<FollowUser> list(String userId, Integer page_size, Integer page_num);
    Long getTotal(String userId);
    List<FollowUser> listFollow(String followId,Integer page_size,Integer page_num);
    Long getTotalFollow(String followId);
    List<FollowUser> listFriend(String userId,Integer page_size,Integer page_num);
}
