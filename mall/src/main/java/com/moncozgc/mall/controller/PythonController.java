package com.moncozgc.mall.controller;

import com.moncozgc.mall.common.CommonResult;
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
        int i = pythonService.PythonDebugImpl();
        if (i == 0) {
            return CommonResult.success("Python调用测试成功");
        } else {
            return CommonResult.success("Python调用测试失败");
        }

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

    @ApiOperation("python-脚本集成接口")
    @ResponseBody
    @RequestMapping(value = "/py_integrate", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public CommonResult<Object> PythonToIntegrate(@RequestParam(defaultValue = "SERVER") String type, String PythonScript) {
        int i = pythonService.PythonToIntegrate(type, PythonScript);
        if (i == 0) {
            return CommonResult.success("PYTHON RUN SUCCESS");
        } else {
            return CommonResult.failed(i, "PYTHON RUN FAILED");
        }
    }
}
