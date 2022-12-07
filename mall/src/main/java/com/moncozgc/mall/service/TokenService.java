package com.moncozgc.mall.service;

import com.moncozgc.mall.dto.User;

import java.util.Date;

/**
 * token接口
 */
public interface TokenService {

    /**
     * 获取token信息
     *
     * @param user 用户信息
     * @param date 有效时间
     * @return token
     */
    public String getToken(User user, Date date);
}