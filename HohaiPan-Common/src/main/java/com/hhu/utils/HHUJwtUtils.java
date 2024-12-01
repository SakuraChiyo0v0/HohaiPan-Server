package com.hhu.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.log.Log;
import com.hhu.properties.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HHUJwtUtils {

    @Autowired
    private JwtProperties jwtProperties;

    //创建Jwt
    public String JwtCreate(Map<String, Object> map){
        map.put("expireTime", System.currentTimeMillis() + jwtProperties.getExpireTime());
        String token = JWTUtil.createToken(map, jwtProperties.getSecretKey().getBytes());
        return token;
    }

    //解析Jwt
    public Map<String, Object> JwtParse(String token){
        JSONObject payloads = JWTUtil.parseToken(token).getPayloads();
        Log.get().info(payloads.toString());
        return payloads;
    }

    //验证Jwt
    public boolean JwtVerify(String token){
        boolean verify = JWTUtil.verify(token, jwtProperties.getSecretKey().getBytes());
        return verify;
    }
}
