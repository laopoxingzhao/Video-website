package com.hu.video;

import com.hu.video.entity.User;
import com.hu.video.entity.VideoFile;
import com.hu.video.service.UserService;
import com.hu.video.service.VideoFileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Date;

@SpringBootTest
class VideoApplicationTests {

    @Autowired
    UserService userService;
    @Autowired
    VideoFileService fileService;
    @Test
    void finUserById() {
        System.out.println(userService.findUerById(1));

    }

    @Test
    void getUserVideos() throws FileNotFoundException {
        System.out.println(fileService.getUserVideos(1).get(0));
        VideoFile videoFile = fileService.getUserVideos(1).get(0);
        File file = new File(String.format("%s\\%s",videoFile.getFilePath(),videoFile.getName()));
        System.out.println(file);
        FileInputStream stream = new FileInputStream(file);
    }
    @Test
    void  register(){
        User user = new User();
        user.setBirth(new Date(1990,12,2));
        user.setName("ssss");
        user.setPassword("abcd");
//        user.setSex("男");
        userService.register(user);

    }

}
