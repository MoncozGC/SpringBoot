package com.moncozgc.mall.service.impl;

import com.moncozgc.mall.dto.HealthWikiWord;
import com.moncozgc.mall.dto.HealthWikiWordDto;
import com.moncozgc.mall.dto.HealthWikiWordVo;
import com.moncozgc.mall.mapper.HealthWikiWordMapper;
import com.moncozgc.mall.service.HealthWikiWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by MoncozGC on 2023/2/14
 */
@Service
public class HealthWikiWordServiceImpl implements HealthWikiWordService {
    @Autowired
    private HealthWikiWordMapper healthWikiWordMapper;

    private static final Function<HealthWikiWord, HealthWikiWordVo> WIKI_ENTITY_TO_VO = wikiWord -> {
        HealthWikiWordVo vo = new HealthWikiWordVo();
        vo.setId(wikiWord.getId());
        vo.setWord(wikiWord.getWord());
        vo.setType(wikiWord.getType());
        return vo;
    };

    @Override
    public List<HealthWikiWordVo> queryHealthWikiWord(HealthWikiWordDto wikiWordDto) {
        HealthWikiWord wikiWord = new HealthWikiWord();
        wikiWord.setWord(wikiWordDto.getKeyword());
        wikiWord.setType(wikiWordDto.getType());

        return healthWikiWordMapper.queryHealthWikiWord(wikiWord)
                .stream()
                .map(WIKI_ENTITY_TO_VO)
                .collect(Collectors.toList());
    }
}
