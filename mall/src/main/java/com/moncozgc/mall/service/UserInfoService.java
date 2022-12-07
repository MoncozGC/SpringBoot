package com.moncozgc.mall.service;

import com.moncozgc.mall.dto.User;

/**
 * 获取用户信息接口类
 */
public interface UserInfoService {
    /**
     * 根据用户id, 获取用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    User getUserInfoById(Integer userId);

    /**
     * 根据用户姓名, 获取用户信息
     *
     * @param userName 用户姓名
     * @return 用户信息
     */
    User getUserInfoByName(String userName);

    /**
     * 添加用户
     *
     * @param User 用户信息
     * @return 新增条数
     */
    int addUser(User User);
}