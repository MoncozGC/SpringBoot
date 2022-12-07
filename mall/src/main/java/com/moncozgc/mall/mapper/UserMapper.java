package com.moncozgc.mall.mapper;

import com.moncozgc.mall.dto.User;
import org.apache.ibatis.annotations.*;

/**
 * 操作数据库
 */
@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user_info WHERE UI_ID=#{userId}")
    User getUserInfoById(@Param("userId") Integer userId);

    @Select("SELECT * FROM user_info WHERE UI_USER_NAME=#{userName}")
    User getUserInfoByName(@Param("userName") String userName);

    @Insert("INSERT INTO user_info ( UI_USER_NAME, UI_PASSWORD, UI_STATUS,UI_CREATE_TIME, UI_ROLES )   VALUES ( #{UI_USER_NAME}, #{UI_PASSWORD},#{UI_STATUS},#{UI_CREATE_TIME},#{UI_ROLES}) ")
    @Options(useGeneratedKeys = true, keyProperty = "UI_ID")
    int addUser(User User);
}