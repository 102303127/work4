package com.zhang.exception;

import com.zhang.vo.base;
import com.zhang.vo.result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class exceptionAdvice {

    @ExceptionHandler(userException.class)
    @ResponseBody
    public result processUserException(userException e){
        log.error("业务异常"+ e);
        return result.Fail("操作错误:" + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public result processException(Exception e){
        log.error("后端异常"+ e);
        return new result(new base(-1,"系统繁忙稍后重试"));
    }
}
