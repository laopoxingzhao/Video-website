package com.hu.video.utils;

import com.hu.video.entity.VideoFile;

import java.io.*;

/**
 *@ClassName FFmepgUtil
 *@Description FFmpeg工具类,生成视频文件
 *@Author 胡
 *@Date 2023/5/16 10:31
 *@Version 1.0
 */
public class FFmepgUtil {

    /**
     * 使用ffmpeg生成视频
     * @return
     * @throws IOException
     */

    public static Boolean createM3u8(VideoFile videoFile) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("ffmpeg", "-i", "play.mp4", "-c:v", "libx264",
                "-preset", "veryfast", "-c:a", "aac",
                "-b:a", "128k", "-f", "hls",
                "-vf","scale","640:360",
                "-hls_time", "10", "-hls_list_size", "0",
                "-hls_base_url", "http://localhost/video/"+videoFile.getFileId()+"/",
                "-hls_segment_filename", "video%3d.ts", "output.m3u8");
        builder.directory(new File("D:\\下载\\Video\\"+videoFile.getUid()+"\\"));
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
        process.waitFor();
        return  true;
    }

    public static void createWJJ(VideoFile videoFile){

//        File newfile = new File(String.format("D:\\下载\\Video\\%s\\%s",videoFile.getUid(),videoFile.getFileId() ));
//        File newfile = new File(String.format("D:\\下载\\Video\\%s\\%s",videoFile.getUid(),videoFile.getFileId() ));
        String path = String.format("D:\\下载\\Video\\%s\\%s",videoFile.getUid(),videoFile.getFileId());
        File newfile360 = new File(path+"\\360");
        File newfile1080 = new File(path+"\\1080");
        newfile360.mkdirs();
        newfile1080.mkdirs();
    }
}
