package com.moncozgc.mall.service.impl;

import com.moncozgc.mall.common.api.CommonResult;
import com.moncozgc.mall.controller.UmsMemberController;
import com.moncozgc.mall.service.RedisService;
import com.moncozgc.mall.service.UmsMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 实现类
 * 生成验证码时，将自定义的Redis键值加上手机号生成一个Redis的key,以验证码为value存入到Redis中，并设置过期时间为自己配置的时间（这里为120s）。
 * 校验验证码时根据手机号码来获取Redis里面存储的验证码，并与传入的验证码进行比对。
 * Created by MoncozGC on 2022/11/22
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberService {

    @Autowired
    private RedisService redisService;
    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    @Value("${redis.key.expire.authCode}")
    private Long REDIS_KEY_EXPIRE_AUTH_CODE;

    private static final Logger logger = LoggerFactory.getLogger(UmsMemberController.class);

    /**
     * 验证码绑定手机号存储到redis并设置过期时间
     *
     * @param telephone 手机号
     * @return 后端结果信息返回
     */
    @Override
    public CommonResult<String> generateAuthCode(String telephone) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        // 生成随机六位的验证码
        for (int i = 0; i <= 6; i++) {
            // nexInt: (0, 10]
            sb.append(random.nextInt(10));
        }
        // 验证码绑定手机号存储到redis并设置过期时间
        redisService.set(REDIS_KEY_PREFIX_AUTH_CODE + telephone, sb.toString());
        redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE + telephone, REDIS_KEY_EXPIRE_AUTH_CODE);
        logger.info("手机号: " + telephone + ", 生成的验证码:" + sb);
        return CommonResult.success(sb.toString(), "获取验证码成功");
    }

    /**
     * //对输入的验证码进行校验
     *
     * @param telephone 手机号
     * @param authCode  验证码
     * @return 后端结果信息返回
     */
    @Override
    public CommonResult<String> verifyAuthCode(String telephone, String authCode) {
        if (authCode.isEmpty()) {
            return CommonResult.failed("请输入验证码");
        }
        String realAuthCode = redisService.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
        boolean result = authCode.equals(realAuthCode);
        if (result) {
            return CommonResult.success(null, "验证成功");
        } else {
            return CommonResult.failed("验证失败");
        }
    }

    /**
     * 移除手机号的key
     *
     * @param telephone 键
     * @return 返回执行结果
     */
    @Override
    public CommonResult<String> deleteAuthCode(String telephone) {
        String telephoneKey = REDIS_KEY_PREFIX_AUTH_CODE + telephone;
        // 查看key是否存在
        if (!redisService.hasKey(telephoneKey)) {
            return CommonResult.failed("key不存在");
        }
        redisService.remove(telephoneKey);
        logger.info("Redis删除KEY: " + telephoneKey);
        return CommonResult.success(telephoneKey, "KEY删除成功");
    }

    @Override
    public CommonResult<Long> getExpireKey(String telephone) {
        String telephoneKey = REDIS_KEY_PREFIX_AUTH_CODE + telephone;
        if (!redisService.hasKey(telephoneKey)) {
            return CommonResult.failed("key不存在");
        }
        Long keyExpireTime = redisService.getExpire(telephoneKey, TimeUnit.SECONDS);
        logger.info("Redis KEY: " + telephoneKey + ", Expire 为: " + keyExpireTime);
        return CommonResult.success(keyExpireTime, "Expire 获取成功");
    }
}
