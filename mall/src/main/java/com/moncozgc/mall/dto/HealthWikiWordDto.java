package com.moncozgc.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by MoncozGC on 2023/2/14
 */
@Data
@ApiModel("健康词条wiki词条查询模型")
public class HealthWikiWordDto {
    @ApiModelProperty("关键词条")
    private String keyword;
    @ApiModelProperty("类型, 1-药品,2-病,3-症状,4-组方")
    private Integer type;
}
