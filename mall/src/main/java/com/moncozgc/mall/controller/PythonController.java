package com.moncozgc.mall.controller;

import com.moncozgc.mall.dto.ExportExcelERP;
import com.moncozgc.mall.service.PythonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by MoncozGC on 2022/12/13
 */
@RestController
@RequestMapping("/python")
public class PythonController {
    @Autowired
    private PythonService pythonService;

    @RequestMapping(value = "/py_debug", method = RequestMethod.POST)
    public void PythonDebug() {
        pythonService.PythonDebugImpl();
    }

    @ResponseBody
    @RequestMapping(value = "/py_toExcel", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public void PythonToOneExcel(@RequestBody ExportExcelERP exportExcelERP) {
        pythonService.PythonToExcelImpl(exportExcelERP);
    }
}
