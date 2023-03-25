package com.hu.video.controller;

import com.hu.video.entity.User;
import com.hu.video.service.UserService;
import com.hu.video.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/login")
    @ResponseBody
    String Login(HttpServletRequest request, HttpServletResponse response,@RequestBody User user) throws IOException {
//        Reader reader = new StringReader(data);
        String name = user.getName();
        String jwt = JWTUtils.getToken(new HashMap<String, String>().put("user", name));
        response.setHeader("hu", String.valueOf(jwt));

        log.info(String.valueOf(user));
//        User user = new ObjectMapper().readValue(data, User.class);
//        log.info(String.valueOf(user));
        return  "1111";
    }

    @PostMapping("/register")
    @ResponseBody
    public String register(@RequestBody User user,HttpServletRequest request){
//        String jwt = request.getHeader()
//        request.
        userService.register(user);
        request.getSession().setAttribute("user",user);
        log.info(String.valueOf(user));

        return "OK";
    }

}
