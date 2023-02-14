package com.moncozgc.mall.controller;

import com.moncozgc.mall.common.CommonResult;
import com.moncozgc.mall.dto.HealthWikiWordDto;
import com.moncozgc.mall.dto.HealthWikiWordVo;
import com.moncozgc.mall.service.HealthWikiWordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by MoncozGC on 2023/2/14
 */
@Api(tags = "关键词模块相关接口")
@RestController
@RequestMapping("wiki")
@Slf4j
public class HealthWikiWordController {
    @Autowired
    private HealthWikiWordService healthWikiWordService;

    @ApiOperation("健康词条查询")
    @PostMapping("/search")
    public CommonResult<List<HealthWikiWordVo>> getItemList(@RequestBody HealthWikiWordDto wikiWordDto) {
        return CommonResult.success(healthWikiWordService.queryHealthWikiWord(wikiWordDto));
    }
}
