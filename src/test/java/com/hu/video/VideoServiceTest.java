package com.hu.video;/**
 * 功能描述
 *
 * @author 胡
 * @date 2023/05/16  15:10
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.hu.video.entity.VideoFile;
import com.hu.video.service.VideoFileService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 *@ClassName VideoServiceTest
 *@Description TODO ..
 *@Author 胡
 *@Date 2023/5/16 15:10
 *@Version 1.0
 */
@SpringBootTest
public class VideoServiceTest {

    @Resource
    VideoFileService fileService;
    @Test
    void a(){
        String vid = "1";
//        LambdaQueryWrapper<VideoFile> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(VideoFile::getFileId,vid).select();

        LambdaQueryChainWrapper<VideoFile> query = fileService.lambdaQuery();
        VideoFile one = query.eq(VideoFile::getFileId, vid).select().one();
        System.out.println(one);
    }
}
