package com.hu.video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.video.entity.User;

public interface UserService extends IService<User> {

    User findUerById(int id);
    boolean register(User user);
}
