package com.moncozgc.mall.controller;

import com.moncozgc.mall.service.DataStructureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by MoncozGC on 2022/12/22
 */
@Api("数据结构")
@Controller
@RequestMapping("/data_struct")
public class DataStructureController {

    @Autowired
    private DataStructureService dataStructureService;

    @ApiOperation("五子棋To稀疏数组")
    @ResponseBody
    @RequestMapping(value = "/go_bang", method = RequestMethod.GET)
    public void GobangToSparseArrayController() {
        dataStructureService.GobangToSparseArray();
    }
}
