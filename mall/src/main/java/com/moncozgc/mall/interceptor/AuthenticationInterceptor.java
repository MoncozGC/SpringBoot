package com.moncozgc.mall.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.moncozgc.mall.annotation.TokenCheck;
import com.moncozgc.mall.annotation.TokenPass;
import com.moncozgc.mall.dto.User;
import com.moncozgc.mall.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * HandlerInterceptor接口实现拦截器，主要用于校验token
 * preHandle方法：此方法会在进入controller之前执行，返回Boolean值决定是否执行后续操作。
 * postHandle方法：此方法将在controller执行之后执行，但是视图还没有解析，可向ModelAndView中添加数据(前后端不分离的)。
 * afterCompletion方法：该方法会在整个请求结束（请求结束，但是并未返回结果给客户端）之后执行， 可获取响应数据及异常信息。
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserInfoService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passToken注解，有则无需进行token校验
        if (method.isAnnotationPresent(TokenPass.class)) {
            TokenPass passToken = method.getAnnotation(TokenPass.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有CheckToken的注解
        if (method.isAnnotationPresent(TokenCheck.class)) {
            TokenCheck CheckToken = method.getAnnotation(TokenCheck.class);
            if (CheckToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new RuntimeException("无token,请重新登录");
                }
                // 获取 token 中的 user id
                String userId;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("您的token已坏掉了,请重新登录获取token");
                }
                User user = userService.getUserInfoById(Integer.valueOf(userId));
                if (user == null) {
                    throw new RuntimeException("用户不存在,请重新登录");
                }
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getUI_PASSWORD())).build();
                try {
                    jwtVerifier.verify(token);

                } catch (InvalidClaimException e) {
                    throw new RuntimeException("无效token,请重新登录获取token");
                } catch (TokenExpiredException e) {
                    throw new RuntimeException("token已过期,请重新登录获取token");
                } catch (JWTVerificationException e) {
                    throw new RuntimeException(e.getMessage());
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }

}