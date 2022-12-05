package com.moncozgc.mall.controller;

import com.moncozgc.mall.common.api.CommonResult;
import com.moncozgc.mall.service.UmsMemberService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 添加根据电话号码获取验证码的接口和校验验证码的接口
 * Created by MoncozGC on 2022/11/22
 */
@Api(tags = "UmsMemberController", description = "会员验证")
@Controller
@RequestMapping("/sso")
public class UmsMemberController {
    @Autowired
    private UmsMemberService memberService;

    /**
     * 获取验证码
     *
     * @param telephone 手机号
     * @return 放回验证码信息
     */
    @RequestMapping(value = "/getAuthCode", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<String> getAuthCode(@RequestParam String telephone) {
        return memberService.generateAuthCode(telephone);
    }

    /**
     * 判断验证码是否正确
     *
     * @param telephone 手机号
     * @param authCode  验证码
     * @return 返回验证结果
     */
    @RequestMapping(value = "/verifyAuthCode", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<String> updatePassword(@RequestParam String telephone, @RequestParam String authCode) {
        return memberService.verifyAuthCode(telephone, authCode);
    }

    /**
     * 删除验证码
     *
     * @param telephone 键
     * @return 返回删除结果
     */
    @RequestMapping(value = "/deleteAuthCode", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<String> deleteAuthCode(@RequestParam String telephone) {
        return memberService.deleteAuthCode(telephone);
    }

    @RequestMapping(value = "/getExpireKey", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Long> getExpireKey(@RequestParam String telephone) {
        return memberService.getExpireKey(telephone);
    }
}
