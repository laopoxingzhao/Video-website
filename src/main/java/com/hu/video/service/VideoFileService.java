package com.hu.video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.video.entity.VideoFile;

import java.util.List;

public interface VideoFileService extends IService<VideoFile>{

    List<VideoFile> getUserVideos(int id);

}
