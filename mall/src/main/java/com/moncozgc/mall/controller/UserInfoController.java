package com.moncozgc.mall.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.moncozgc.mall.annotation.TokenCheck;
import com.moncozgc.mall.annotation.TokenPass;
import com.moncozgc.mall.common.api.CommonResult;
import com.moncozgc.mall.dto.User;
import com.moncozgc.mall.service.TokenService;
import com.moncozgc.mall.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录注册, token验证
 * https://blog.csdn.net/qq_35387940/article/details/103307346
 *
 * 用户注册: 使用Spring Security的BCryptPasswordEncoder进行密码加密, 存库
 * 用户登录: 查库, 使用Spring Security的BCryptPasswordEncoder进行密码校验. 若登录成功返回JWT生成带有过期时间的token
 * token校验: 使用JWT token校验, 错误或者过去则拦截, 正常则继续访问
 */
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    UserInfoService userService;
    @Autowired
    TokenService tokenService;

    @Value("${EXPIRE_TIME}")
    private String EXPIRE_TIME;

    /**
     * 获取用户信息
     *
     * @param userName 账户名
     */
    @TokenCheck
    @GetMapping("/getUserByName/{userName}")
    public String getUser(@PathVariable("userName") String userName) {
        User userInfoByName = userService.getUserInfoByName(userName);
        return userInfoByName.toString();

    }

    /**
     * 注册接口
     *
     * @param userInfo 用户信息
     */
    @TokenPass
    @PostMapping("/register")
    public CommonResult<Object> register(@RequestBody Map<String, Object> userInfo) {
        String username = String.valueOf(userInfo.get("username"));
        String password = String.valueOf(userInfo.get("password"));
        User dbUserName = userService.getUserInfoByName(username);

        if (StrUtil.hasEmpty(username) || StrUtil.hasEmpty(password)) return CommonResult.failed("注册失败, 用户名或密码为空");
        if (!BeanUtil.isEmpty(dbUserName)) return CommonResult.failed("注册失败, 用户名已存在");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodePwd = bCryptPasswordEncoder.encode(password);
        User User = new User();
        User.setUI_USER_NAME(username);
        User.setUI_PASSWORD(encodePwd);
        User.setUI_STATUS("0");
        User.setUI_CREATE_TIME(System.currentTimeMillis());
        User.setUI_ROLES(String.valueOf(userInfo.get("roles")));

        int i = userService.addUser(User);
        if (i == 1) {
            return CommonResult.success("注册成功");
        }
        return CommonResult.failed("注册失败");

    }

    /**
     * 登录接口
     *
     * @param user 用户信息
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, Object> user) {
        Map<String, Object> result = new HashMap<>();
        User userForBase = userService.getUserInfoByName(String.valueOf(user.get("username")));
        if (userForBase == null) {

            result.put("message", "登录失败,用户不存在");
            return result;
        } else {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String dbPwd = userForBase.getUI_PASSWORD();
            boolean matchesResult = bCryptPasswordEncoder.matches(String.valueOf(user.get("password")), dbPwd);
            if (!matchesResult) {
                result.put("message", "登录失败,密码错误");
                return result;
            } else {
                Date expiresDate = new Date(System.currentTimeMillis() + (long) Integer.parseInt(EXPIRE_TIME) * 60 * 1000);

                String token = tokenService.getToken(userForBase, expiresDate);
                result.put("token", token);
                result.put("expireTime", EXPIRE_TIME);
                result.put("userId", userForBase.getUI_ID());
                return result;
            }
        }
    }

    /**
     * 检测token是否生效
     */
    @TokenCheck
    @GetMapping("/afterLogin")
    public String afterLogin() {

        return "你已通过验证,成功进入系统";
    }


}