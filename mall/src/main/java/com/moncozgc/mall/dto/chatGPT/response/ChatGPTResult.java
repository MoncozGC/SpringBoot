package com.moncozgc.mall.dto.chatGPT.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by MoncozGC on 2023/2/19
 */
@Data
@ApiModel()
public class ChatGPTResult {
    @ApiModelProperty(value = "主键ID")
    private Long oid;

    @ApiModelProperty(value = "提问内容")
    private String question;

    @ApiModelProperty(value = "回答内容")
    private String answer;

    @ApiModelProperty(value = "提问用户ID")
    private Long userId;

    @ApiModelProperty(value = "提问时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "提问类型, 1-智能询药, 2-智能组方")
    private Integer questionType;
}
