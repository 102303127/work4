package com.zhang.controller;


import com.zhang.pojo.User;
import com.zhang.service.UserService;
import com.zhang.utils.FileUtils;
import com.zhang.vo.result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhang
 * &#064;date  2024/2/10
 * &#064;Description  文件上传并校验Controller层
 */
@RestController
public class FileController {

    @Autowired
    FileUtils fileUtils;
    @Autowired
    UserService userService;

    /**
     * 先对文件头像进行检查是否是图片，在上传到本地仓库
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PutMapping("/avatar/uploads")
    public result update(@RequestBody MultipartFile file, HttpServletRequest request) throws IOException {
        // 原始文件名称
        String fileName = file.getOriginalFilename();

        // 解析到文件后缀
        int index = fileName.lastIndexOf(".");
        String suffix;
        if (index == -1 || (suffix = fileName.substring(index + 1)).isEmpty()) {
            String msg= "文件后缀不能为空";
            return new result(-1,msg);
        }
        // 允许上传的文件后缀列表
        Set<String> allowSuffix = new HashSet<>(Arrays.asList("jpg", "jpeg", "png", "gif"));
        if (!allowSuffix.contains(suffix.toLowerCase())) {
            String msg="非法的文件，不允许的文件类型：" + suffix;
            return new result(-1,msg);
        }

        String url=fileUtils.uploads(file);
        boolean flag= url!=null;
        if(flag){
            /*String token = request.getHeader("token");*/
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user.setAvatarUrl(url);
            userService.update(user);
        }

        return new result(flag ? 10000:-1,flag?url:"上传头像失败");
    }
}
