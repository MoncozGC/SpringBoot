package com.moncozgc.mall.controller;

import com.moncozgc.mall.common.api.CommonResult;
import com.moncozgc.mall.dto.ExportExcelERP;
import com.moncozgc.mall.service.PythonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by MoncozGC on 2022/12/13
 */
@Api(tags = "Python程序接口")
@RestController
@RequestMapping("/python")
public class PythonController {
    @Autowired
    private PythonService pythonService;

    @ApiOperation("python接口测试")
    @RequestMapping(value = "/py_debug", method = RequestMethod.GET)
    public CommonResult<Object> PythonDebug() {
        pythonService.PythonDebugImpl();
        return CommonResult.success("Python调用测试成功");
    }

    @ApiOperation("python-根据指定文件筛选指定列")
    @ResponseBody
    @RequestMapping(value = "/py_toExcel", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public CommonResult<Object> PythonToOneExcel(@RequestBody ExportExcelERP exportExcelERP) {
        int i = pythonService.PythonToExcelImpl(exportExcelERP);
        if (i == 0) {
            return CommonResult.success("EXCEL操作成功");
        } else {
            return CommonResult.failed(i, "EXCEL操作失败");
        }
    }
}
