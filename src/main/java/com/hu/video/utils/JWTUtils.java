package com.hu.video.utils;


import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j

public class JWTUtils {
    private static String SIGNATURE = "token!@#$%^7890";

    /**
     * 生成token
     * @param claims //传入payload
     * @return 返回token
     */
    public static String createToken(Map<String,Object> claims){
//        HashMap<String, Object> claims = new HashMap<>();
//        claims.put("uid",11111);
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,SIGNATURE)//签发算法， 密钥SIGNATURE
                .setClaims(claims)//body数据，要唯一
                .setIssuedAt(new Date())//签发时间
                .setExpiration(new Date(System.currentTimeMillis()+24*60*60*60*1000)); //过期时间 一天
        return jwtBuilder.compact();
    }

    public  static  Map<String,Object> checkToken(String token ){
        try{
            Jwt parse = Jwts.parser().setSigningKey(SIGNATURE).parse(token);
            return (Map<String, Object>) parse.getBody();
        }
        catch (Exception e){
//            e.printStackTrace();
            log.info(e.getMessage());
        }
        return null;
    }
}
