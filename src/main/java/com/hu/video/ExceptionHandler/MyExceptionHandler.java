package com.hu.video.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@Slf4j
public class MyExceptionHandler {

    @ExceptionHandler(value =java.sql.SQLIntegrityConstraintViolationException.class)
//    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public String sqlexceptionHandler(Exception e, HttpServletResponse response){
        log.info("dswjdfio"+String.valueOf(e));
//        response.
        return "用户名已存在";
//
    }


}
