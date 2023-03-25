package com.hu.video.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoFile  {

    Integer fileId;

    Integer uid;

    String name;

    String filePath;


//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    Date date;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Timestamp date;


}
