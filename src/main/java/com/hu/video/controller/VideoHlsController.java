package com.hu.video.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.hu.video.entity.VideoFile;
import com.hu.video.service.VideoFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;


@Controller
@RequestMapping("video")
@Slf4j
public class VideoHlsController {


    @Resource
    private VideoFileService fileService;

    @GetMapping("/{vid}/playlist.m3u8")
    public void playlist(@PathVariable String vid,String clarity,HttpServletResponse response) throws Exception {
        log.info("clarity",clarity);
//        fileService.
        response.setContentType("application/vnd.apple.mpegurl");
        String fileName = "D:\\下载\\Video\\1\\1\\360\\output.m3u8";
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        try {
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);

            String line;
            System.out.println(bufferedReader.lines());
            while ((line = bufferedReader.readLine()) != null) {
//                System.out.println(line);
                response.getWriter().println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @RequestMapping(value = "/{vid}/{clarity}/{segment}", method = RequestMethod.GET)
    public void segment(@PathVariable String vid,@PathVariable String clarity,@PathVariable String segment, HttpServletResponse response) throws Exception {

        LambdaQueryChainWrapper<VideoFile> query = fileService.lambdaQuery();
        VideoFile one = query.eq(VideoFile::getFileId, vid).select().one();
//        System.out.println(one);

        File file = new File("D:\\下载\\Video\\"+one.getUid()+"\\"+one.getFileId()+"\\360\\" + segment);
        if (!file.exists()) {
            throw new FileNotFoundException("Segment not found.");
        }
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("Content-Length", String.valueOf(file.length()));
        FileInputStream inputStream = new FileInputStream(file);
        IOUtils.copy(inputStream, response.getOutputStream());
        IOUtils.closeQuietly(inputStream);
    }

}
