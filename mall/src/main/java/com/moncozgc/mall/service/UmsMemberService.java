package com.moncozgc.mall.service;

import com.moncozgc.mall.common.api.CommonResult;

/**
 * 会员管理Service
 * Created by MoncozGC on 2022/11/22
 */
public interface UmsMemberService {

    /**
     * 生成验证码
     *
     * @param telephone 手机号
     */
    CommonResult generateAuthCode(String telephone);

    /**
     * 判断验证码和手机号是否匹配
     *
     * @param telephone 手机号
     * @param authCode  验证码
     */
    CommonResult verifyAuthCode(String telephone, String authCode);

}
