package com.hu.video.service.impl;

import com.hu.video.entity.VideoFile;
import com.hu.video.mapper.VideoFileMapper;
import com.hu.video.service.VideoFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoFileServiceImpl implements VideoFileService {

    @Autowired
    VideoFileMapper fileMapper;


    @Override
    public List<VideoFile> getUserVideos(int id) {
        return fileMapper.getUserAllVideoByuid(id);
    }
}
