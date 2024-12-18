package com.hhu.interceptor;


import cn.hutool.core.util.StrUtil;
import com.hhu.constant.RedisConstant;
import com.hhu.properties.JwtProperties;
import com.hhu.utils.HHUJwtUtils;
import com.hhu.utils.HHUThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.hhu.constant.RedisConstant.LOGIN_TOKEN_KEY;

/**
 * jwt令牌校验的拦截器
 */
@Component
@Slf4j
public class JwtTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private HHUJwtUtils hhuJwtUtils;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            //return true;
        }

        try{
            //从请求头中获取令牌
            String token = request.getHeader(jwtProperties.getTokenName());
            if(StrUtil.isBlank(token)){
                return true;
            }
            //用户存在则刷新时间 不存在直接跳过
            log.info("jwt校验:{}",token);
            String tokenKey = LOGIN_TOKEN_KEY + token;

            Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(tokenKey);
            log.info("刷新token:{}",entries);
            if(entries.isEmpty()){
                return true;
            }
            //将业务数据保存到TreadLocal中
            HHUThreadLocalUtil.set(entries);
            //刷新token有效期
            stringRedisTemplate.expire(tokenKey,jwtProperties.getExpireTime(), TimeUnit.SECONDS);

            Long userId = Long.valueOf(entries.get("userId").toString());
            log.info("用户{}触发Token拦截器",userId);
            return true;
        }catch(Exception ex){
            //不通过，响应401状态码
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空ThreadLocal中的数据
        HHUThreadLocalUtil.remove();
    }
}
