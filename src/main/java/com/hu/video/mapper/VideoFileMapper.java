package com.hu.video.mapper;


import com.hu.video.entity.VideoFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VideoFileMapper {

    @Select("select * from video_file where uid =#{uid}")
    List<VideoFile> getUserAllVideoByuid(int uid);

}
