package com.hu.video.service;

import com.hu.video.entity.VideoFile;

import java.util.List;

public interface VideoFileService {

    List<VideoFile> getUserVideos(int id);

}
