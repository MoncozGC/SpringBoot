package com.moncozgc.mall.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.moncozgc.mall.dto.User;
import com.moncozgc.mall.service.TokenService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * token实现类
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Override
    public String getToken(User user, Date date) {
        String token = "";

        token = JWT.create()
                .withAudience(String.valueOf(user.getUI_ID())) // 账户id
                .withExpiresAt(date) // 过期时间配置
                .sign(Algorithm.HMAC256(user.getUI_PASSWORD())); // 加密配置
        return token;
    }
}