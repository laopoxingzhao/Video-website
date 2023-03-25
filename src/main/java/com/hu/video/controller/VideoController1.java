package com.hu.video.controller;

import com.hu.video.entity.VideoFile;
import com.hu.video.service.VideoFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/video")
@Slf4j
public class VideoController1 {

    @Autowired
    VideoFileService videoFileService;

    @RequestMapping("/d")
    String d(){
        return "video.html";
    }


    @RequestMapping("/{fileid}")
    void getVideo(HttpServletRequest request, HttpServletResponse response, @PathVariable int fileid){
        //获取视频文件流
        FileInputStream fileInputStream = null;
        OutputStream outputStream = null;
        File videoFile = null;
        List<VideoFile> userVideos = videoFileService.getUserVideos(1);

        String path = null;
        for (VideoFile video : userVideos) {
            if (video.getFileId() == fileid) {
                path = String.format("%s\\%s",video.getFilePath(),video.getName());
                break;
            }
        }


        try {
            outputStream = response.getOutputStream();

            videoFile = new File(path);
            fileInputStream = new FileInputStream(videoFile);


            byte[] cache = new byte[1024];
            response.setHeader(HttpHeaders.CONTENT_TYPE, "video/mp4");
            response.setHeader(HttpHeaders.CONTENT_LENGTH, fileInputStream.available()+"");
            int flag;
            while ((flag = fileInputStream.read(cache)) != -1) {
                outputStream.write(cache, 0, flag);
            }
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){
            log.error("文件传输错误", e);
//            throw new RuntimeException("文件传输错误");
        } finally{
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("流释放错误", e);
                }
            }
            if(fileInputStream != null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    log.error("文件流释放错误", e);
                }
            }

        }

    }
    /**
     * 获取视频流
     * @param response
     * @param videoId 视频存放信息索引
     */
    @RequestMapping("/getVideo")
    public void getVideo(HttpServletRequest request,HttpServletResponse response,Integer videoId)
    {
        //数据库视频信息
//        Video video = videoService.selectById(videoId);
        VideoFile videoSource = videoFileService.getUserVideos(1).get(0);



        response.reset();
        //获取从那个字节开始读取文件
        String rangeString = request.getHeader("Range");

        try {
            //获取响应的输出流
            OutputStream outputStream = response.getOutputStream();
            File file = new File(videoSource.getFilePath()+'\\'+videoSource.getName());
            if(file.exists()){
                RandomAccessFile targetFile = new RandomAccessFile(file, "r");
                long fileLength = targetFile.length();
                //播放
                if(rangeString != null){

                    long range = Long.valueOf(rangeString.substring(rangeString.indexOf("=") + 1, rangeString.indexOf("-")));
                    //设置内容类型
                    response.setHeader("Content-Type", "video/mp4");
                    //设置此次相应返回的数据长度
                    response.setHeader("Content-Length", String.valueOf(fileLength - range));
                    //设置此次相应返回的数据范围
                    response.setHeader("Content-Range", "bytes "+range+"-"+(fileLength-1)+"/"+fileLength);
                    //返回码需要为206，而不是200
                    response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                    //设定文件读取开始位置（以字节为单位）
                    targetFile.seek(range);
                }else {//下载

                    //设置响应头，把文件名字设置好
                    response.setHeader("Content-Disposition", "attachment; filename="+videoSource.getName() );
                    //设置文件长度
                    response.setHeader("Content-Length", String.valueOf(fileLength));
                    //解决编码问题
                    response.setHeader("Content-Type","application/octet-stream");
                }


                byte[] cache = new byte[1024 * 300];
                int flag;
                while ((flag = targetFile.read(cache))!=-1){
                    outputStream.write(cache, 0, flag);
                }
            }else {
                String message = "file:"+videoSource.getName()+" not exists";
                //解决编码问题
                response.setHeader("Content-Type","application/json");
                outputStream.write(message.getBytes(StandardCharsets.UTF_8));
            }

            outputStream.flush();
            outputStream.close();

        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
    }


}
