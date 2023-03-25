package com.hu.video.controller;

import com.hu.video.entity.VideoFile;
import com.hu.video.service.VideoFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.List;

@RestController
public class VideoController {
	@Autowired
	VideoFileService videoFileService;
	@GetMapping("play")
	public void play(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.reset();
		List<VideoFile> userVideos = videoFileService.getUserVideos(1);

		String path = null;
		for (VideoFile video : userVideos) {
			if (video.getFileId() == 1) {
				path = String.format("%s\\%s",video.getFilePath(),video.getName());
				break;
			}
		}

		File file = new File(path);
		long fileLength = file.length();
		// 随机读文件
		RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");

		//获取从那个字节开始读取文件
		String rangeString = request.getHeader("Range");
		long range=0;
		if (rangeString!=null && !rangeString.isEmpty()) {
			range = Long.valueOf(rangeString.substring(rangeString.indexOf("=") + 1, rangeString.indexOf("-")));
		}
		//获取响应的输出流
		OutputStream outputStream = response.getOutputStream();
		//设置内容类型
		response.setHeader("Content-Type", "video/mp4");
		//返回码需要为206，代表只处理了部分请求，响应了部分数据
		response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);

		// 移动访问指针到指定位置
		randomAccessFile.seek(range);
		// 每次请求只返回1MB的视频流
		byte[] bytes = new byte[1024 * 1024];
		int len = randomAccessFile.read(bytes);
		//设置此次相应返回的数据长度
		response.setContentLength(len);
		//设置此次相应返回的数据范围
		response.setHeader("Content-Range", "bytes "+range+"-"+(fileLength-1)+"/"+fileLength);
		// 将这1MB的视频流响应给客户端
		outputStream.write(bytes, 0, len);
		outputStream.close();
		randomAccessFile.close();

		System.out.println("返回数据区间:【"+range+"-"+(range+len)+"】");
	}
}