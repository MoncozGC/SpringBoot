package com.moncozgc.mall.service;

import com.moncozgc.mall.dto.HealthWikiWordDto;
import com.moncozgc.mall.dto.HealthWikiWordVo;

import java.util.List;

/**
 * Created by MoncozGC on 2023/2/14
 */
public interface HealthWikiWordService {
    /**
     * 根据关键字查询对应信息
     *
     * @param wikiWordDto 健康词条wiki词条查询模型
     * @return 健康词条模型返回
     */
    List<HealthWikiWordVo> queryHealthWikiWord(HealthWikiWordDto wikiWordDto);
}
