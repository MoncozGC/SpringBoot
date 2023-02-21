package com.moncozgc.mall.dto.chatGPT.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by MoncozGC on 2023/2/19
 */
@Data
@ApiModel
public class ChatGPTDto {

    @ApiModelProperty(value = "症状集合")
    private List<String> symptomList;

    @ApiModelProperty(value = "病种集合")
    private List<String> diseaseList;

    @ApiModelProperty(value = "药品集合")
    private List<String> drugList;

    @ApiModelProperty(value = "提问类型, 1-智能询药, 2-智能组方")
    private Integer questionType;

    @ApiModelProperty(value = "用户ID")
    private Long userId;
}
