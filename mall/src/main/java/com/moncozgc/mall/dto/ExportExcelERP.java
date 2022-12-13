package com.moncozgc.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by MoncozGC on 2022/12/13
 */
@Data
@ApiModel
public class ExportExcelERP {
    @ApiModelProperty(value = "指定的文件路径")
    private String file_path;
    @ApiModelProperty(value = "指定的文件名称")
    private String file_name;
    @ApiModelProperty(value = "指定导出的列")
    private String choice_colum;


}
