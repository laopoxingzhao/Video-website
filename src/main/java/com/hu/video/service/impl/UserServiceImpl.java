package com.hu.video.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.video.entity.User;
import com.hu.video.mapper.UserMapper;
import com.hu.video.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User findUerById(int id) {
        return userMapper.getUserById(id);
    }

    @Override
    public boolean register(User user) {
        return userMapper.setUser(user);
    }
}
