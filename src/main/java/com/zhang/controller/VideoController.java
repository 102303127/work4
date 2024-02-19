package com.zhang.controller;

import com.zhang.pojo.User;
import com.zhang.pojo.Video;
import com.zhang.service.UserService;
import com.zhang.service.VideoService;
import com.zhang.utils.DataUtils;
import com.zhang.utils.FileUtils;
import com.zhang.vo.result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author zhang
 * &#064;date  2024/2/10
 * &#064;Description  视频Controller层
 */
@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private VideoService videoService;
    @Autowired
    private UserService userService;
    @Autowired
    private FileUtils fileUtils;
    /**
     * 视频流
     *
     * @param timestamp
     * @return
     */
    @GetMapping("/feed")
    public result feed(@RequestParam(value = "latest_time",required = false)String timestamp){
        List<Video> video = videoService.feed(timestamp);
        boolean flag=video!=null;
        if(flag) return result.OK(video);
        else return result.Fail();
    }
    /**
     * 投稿
     * @param file
     * @param title
     * @param description
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("/publish")
    public result publish(MultipartFile file,
                          @RequestParam("title") String title,
                          @RequestParam("description") String description,
                          HttpServletRequest request) throws IOException {
        String url = fileUtils.uploads(file);
        boolean flag=url!=null;
        if(flag){
            String token = request.getHeader("token");
            User user = userService.getCurrentUser(token);
            videoService.publish(user.getId(), url, title, description);
            return result.OK(url);
        }
        else return result.Fail();
    }
    /**
     * 发布列表
     *
     * @param userId
     * @param page_num
     * @param page_size
     * @return
     */
    @GetMapping("/list")
    public result list(@RequestParam("user_id") String userId,
                       @RequestParam("page_num") Integer page_num,
                       @RequestParam("page_size") Integer page_size){
        List<Video> list = videoService.list(Long.parseLong(userId), page_num, page_size);
        Long total = videoService.getTotal(Long.parseLong(userId));
        boolean flag=list!=null;
        if(flag) return result.OK(list,total);
        else return result.Fail();
    }

    /**
     * 搜索视频
     * @param keywords
     * @param page_size
     * @param page_num
     * @param from_date
     * @param to_date
     * @param username
     * @return
     */
    @PostMapping("/search")
    public result search(@RequestParam("keywords") String keywords,
                         @RequestParam("page_size") Integer page_size,
                         @RequestParam("page_num") Integer page_num,
                         @RequestParam(value = "from_date",required = false) String from_date,
                         @RequestParam(value = "to_date",required = false) String to_date,
                         @RequestParam(value = "username",required = false) String username){
        String fromDate = DataUtils.validation(from_date);
        String toDate = DataUtils.validation(to_date);
        String userName = DataUtils.validation(username);
        List<Video> video = videoService.search(keywords, page_num, page_size,
                fromDate,toDate, userName);
        boolean flag=video!=null;
        if(flag) return result.OK(video, (long) video.size());
        else return result.Fail();

    }

    /**
     * 视频热门排行榜
     * @param page_size
     * @param page_num
     * @return
     */
    @GetMapping("/popular")
    public result popular(@RequestParam("page_size") Integer page_size,
                          @RequestParam("page_num") Integer page_num){
        List<Video> video = videoService.popular(page_size, page_num);
        boolean flag=video!=null;
        if (flag) return result.OK(video);
        return result.Fail();
    }
}
