package com.moncozgc.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel("base64转换图片模型")
@Data
public class Base64Dto {
    @ApiModelProperty(value = "base64str")
    private String base64str;
}
