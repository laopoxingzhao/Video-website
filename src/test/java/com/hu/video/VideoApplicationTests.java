package com.hu.video;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hu.video.entity.User;
import com.hu.video.entity.VideoFile;
import com.hu.video.service.UserService;
import com.hu.video.service.VideoFileService;
import com.hu.video.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.ibatis.jdbc.Null;
import org.apache.tomcat.jni.Time;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

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
    @Test
    void createTokenTest(){
        Map put = new HashMap<>();
        put.put("id", "hu");
        Object token = JWTUtils.createToken(put);
        System.out.println(token);
    }

    @Test
    void checkTokenTest(){
        Map<String, Object> map = JWTUtils.checkToken("eyJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjExMTExLCJleHAiOjE2ODQ5MzYzMzAsImlhdCI6MTY4NDA0NzI5N30.wWYukCmTOCNtkijnfwE2kiDC5N9CWlzzrN4Re3ensac");
        System.out.println(map);

    }

    @Test
    void ffmpegTest() throws IOException {
//        ProcessBuilder pb = new ProcessBuilder("ffmpeg", "-i", "play.mp4", "-c:v", "copy", "-c:a", "copy", "output.mp4");
        ProcessBuilder pb = new ProcessBuilder("ffmpeg", "-i", "play.mp4", "-c:v", "copy", "-c:a", "copy", "output.mp4");
        pb.directory(new File("D:\\下载\\Video"));
        Process p = pb.start();
//ffmpeg -i input.mp4 -c:v libx264 -preset veryfast -c:a aac -b:a 128k -f hls -hls_time 10 -hls_list_size 0 output.m3u8
//        "ffmpeg -i .\play.mp4 -vn -c:a copy output.aac"
    }

    @Test
    void ffmpegTest2() throws InterruptedException, IOException {
        // 设置FFmpeg命令
        ProcessBuilder pb = new ProcessBuilder("ffmpeg", "-i", "play.mp4", "-c:v", "libx264", "-preset", "veryfast", "-c:a", "aac", "-b:a", "128k", "-f", "hls", "-hls_time", "10", "-hls_list_size", "0", "output.m3u8");
        pb.redirectErrorStream(true);
        // 设置工作目录（可选）
        pb.directory(new File("D:\\下载\\Video\\11"));

        // 启动进程并等待进程结束
        Process p = pb.start();
        Thread.sleep(2000);
//         监听子进程的输出流
        InputStream is = p.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;

        while ((line = br.readLine()) != null) {
            System.out.println("ss"+line);
            // 在此处处理子进程的输出内容
        }
//         等待进程结束
        int exitCode = p.waitFor();
        System.out.println("FFmpeg process exited with code " + exitCode);
    }
    @Test

    void A() throws IOException, InterruptedException {
//         ffmpeg -i play.mp4 -codec:v h264 -codec:a aac -f hls  -hls_base_url http://example.com/videos/ -hls_segment_filename "video%3d.ts" output.m3u8
//        ProcessBuilder builder = new ProcessBuilder("ffmpeg", "-i", "play.mp4", "-c:v", "libx264", "-preset", "veryfast", "-c:a", "aac", "-b:a", "128k", "-f", "hls", "-hls_time", "10", "-hls_list_size", "5", "output.m3u8");
        ProcessBuilder builder = new ProcessBuilder("ffmpeg", "-i", "play.mp4", "-c:v", "libx264",
                "-preset", "veryfast", "-c:a", "aac",
                "-b:a", "128k", "-f", "hls",
                "-hls_time", "10", "-hls_list_size", "0",
                "-hls_base_url", "http://localhost/video/",
                "-hls_segment_filename", "video%3d.ts", "output.m3u8");
        builder.directory(new File("D:\\下载\\Video\\11"));
        Process process = builder.start();
        InputStream errorStream = process.getErrorStream();
        InputStreamReader isr = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        if (br != null) {
            br.close();
        }
        if (isr != null) {
            isr.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }
    }
}
