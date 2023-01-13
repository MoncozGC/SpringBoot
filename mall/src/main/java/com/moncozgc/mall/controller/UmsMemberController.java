package com.moncozgc.mall.controller;

import com.moncozgc.mall.common.CommonResult;
import com.moncozgc.mall.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "会员接口")
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
    @ApiOperation("获取验证码")
    @ResponseBody
    @RequestMapping(value = "/getAuthCode", method = RequestMethod.GET)
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
    @ApiOperation("判断验证码是否正确")
    @ResponseBody
    @RequestMapping(value = "/verifyAuthCode", method = RequestMethod.POST)
    public CommonResult<String> updatePassword(@RequestParam String telephone, @RequestParam String authCode) {
        return memberService.verifyAuthCode(telephone, authCode);
    }

    /**
     * 删除验证码
     *
     * @param telephone 键
     * @return 返回删除结果
     */
    @ApiOperation("删除验证码")
    @ResponseBody
    @RequestMapping(value = "/deleteAuthCode", method = RequestMethod.POST)
    public CommonResult<String> deleteAuthCode(@RequestParam String telephone) {
        return memberService.deleteAuthCode(telephone);
    }

    @ApiOperation("获取验证码过期时间")
    @ResponseBody
    @RequestMapping(value = "/getExpireKey", method = RequestMethod.GET)
    public CommonResult<Long> getExpireKey(@RequestParam String telephone) {
        return memberService.getExpireKey(telephone);
    }
}
