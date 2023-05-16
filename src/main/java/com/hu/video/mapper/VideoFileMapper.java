package com.hu.video.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hu.video.entity.VideoFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VideoFileMapper extends BaseMapper<VideoFile> {

    @Select("select * from video_file where uid =#{uid}")
    List<VideoFile> getUserAllVideoByuid(int uid);

}
