package com.moncozgc.mall.controller;

import com.moncozgc.mall.common.api.CommonResult;
import com.moncozgc.mall.common.utils.QRCodeUtil;
import com.moncozgc.mall.dto.Base64Dto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * QRCodeController
 * </p>
 *
 * @author rcbb.cc
 * @date 2022/8/28
 */
@Slf4j
@RestController
@RequestMapping("/qrcode")
public class QRCodeController {

    /**
     * 根据 content 生成二维码
     * http://localhost:8089/qrcode/getQRCodeBase64?content=https://www.baidu.com&logoUrl=https://www.baidu.com&width=100&height=100
     *
     * @param content 文本内容
     * @param logoUrl log图标地址
     * @param width   图片宽度
     * @param height  图片高度
     */
    @GetMapping("/getQRCodeBase64")
    public CommonResult<Object> getQRCode(@RequestParam("content") String content,
                                          @RequestParam(value = "logoUrl", required = false) String logoUrl,
                                          @RequestParam(value = "width", required = false) Integer width,
                                          @RequestParam(value = "height", required = false) Integer height) {

        if (width != null && height != null) {
            return CommonResult.success(QRCodeUtil.getBase64QRCode(content, width, height, logoUrl, 100, 100), "二维码转换成功(添加LOG)");
        }
        log.info(">>> 二维码生成成功");
        return CommonResult.success(QRCodeUtil.getBase64QRCode(content, logoUrl), "二维码转换成功");
    }

    /**
     * 根据 content 生成二维码
     *
     * @param response 请求协议
     * @param content  文本内容
     * @param logoUrl  图标地址
     */
    @GetMapping(value = "/getQRCode")
    public void getQRCode(HttpServletResponse response,
                          @RequestParam("content") String content,
                          @RequestParam(value = "logoUrl", required = false) String logoUrl) throws Exception {
        ServletOutputStream stream = null;
        try {
            stream = response.getOutputStream();
            QRCodeUtil.getQRCode(content, logoUrl, stream);
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }

    /**
     * base64转换为图片
     *
     * @param base64Dto base64
     */
    @RequestMapping(value = "/QRCodeBase64ToPic", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public CommonResult<Object> Base64ToImg2(@RequestBody Base64Dto base64Dto) throws IOException {
        QRCodeUtil.GenerateImage(base64Dto, System.getProperty("user.dir") + "\\mall\\src\\main\\data");
        log.info(">>> base64Topic完成");
        return CommonResult.success("转换成功");
    }


}