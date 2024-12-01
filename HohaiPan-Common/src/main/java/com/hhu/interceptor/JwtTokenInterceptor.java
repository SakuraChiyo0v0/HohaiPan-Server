package com.hhu.interceptor;


import com.hhu.properties.JwtProperties;
import com.hhu.utils.HHUJwtUtils;
import com.hhu.utils.HHUThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/**
 * jwt令牌校验的拦截器
 */
@Component
@Slf4j
public class JwtTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private HHUJwtUtils hhuJwtUtils;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }
        //从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getTokenName());
        try{
            log.info("jwt校验:{}",token);
            Map<String, Object> claims = hhuJwtUtils.JwtParse(token);
            Long userId = Long.valueOf(claims.get("userId").toString());
            log.info("当前用户id:{}",userId);

            //将业务数据保存到TreadLocal中
            HHUThreadLocalUtil.set(claims);

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
