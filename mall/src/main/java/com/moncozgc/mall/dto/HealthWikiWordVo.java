package com.moncozgc.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by MoncozGC on 2023/2/14
 */
@Data
@ApiModel("健康词条模型返回")
public class HealthWikiWordVo {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("关键词条")
    private String word;
    @ApiModelProperty("类型, 1-药品,2-病,3-症状,4-组方")
    private Integer type;
}
