package com.hu.video.service;

import com.hu.video.entity.User;

public interface UserService {

    User findUerById(int id);
    boolean register(User user);
}
