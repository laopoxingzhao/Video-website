package com.hu.video.controller;

import com.hu.video.entity.VideoFile;
import com.hu.video.service.VideoFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("video")
@Slf4j
public class VideoController1 {
    @RequestMapping(value = "/playlist.m3u8", method = RequestMethod.GET)
    public void playlist(HttpServletResponse response) throws Exception {

        response.setContentType("application/vnd.apple.mpegurl");


        String fileName = "D:\\下载\\Video\\11\\output.m3u8";
//        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                System.out.println(line);
//                response.getWriter().println(line);
//            }
//        }
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        try {
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
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

    private List<String> getPlaylistSegments() {
        return null;
    }

    @RequestMapping(value = "/{segment}", method = RequestMethod.GET)
    public void segment(@PathVariable String segment, HttpServletResponse response) throws Exception {
        System.out.println(segment);
        File file = new File("D:\\下载\\Video\\11\\" + segment);
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
