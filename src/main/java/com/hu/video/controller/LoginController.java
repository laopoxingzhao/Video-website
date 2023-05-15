package com.hu.video.controller;

import com.hu.video.entity.User;
import com.hu.video.service.UserService;
//import com.hu.video.utils.JWTUtils;
import com.hu.video.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @Autowired
    UserService userService;
    @PostMapping
    @ResponseBody
    String Login(@RequestBody User user) throws IOException {

        HashMap<String, Object> map = new HashMap<>();
        map.put("user",user.getName());
        String s = JWTUtils.createToken(map);


        log.info(String.valueOf(user));
//        User user = new ObjectMapper().readValue(data, User.class);
//        log.info(String.valueOf(user));
        return  s;
    }

    @PostMapping("/register")
    @ResponseBody
    public String register(@RequestBody User user,HttpServletRequest request){
        userService.register(user);
        request.getSession().setAttribute("user",user);
        log.info(String.valueOf(user));

        return "OK";
    }

}
