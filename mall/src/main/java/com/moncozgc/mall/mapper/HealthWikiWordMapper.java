package com.moncozgc.mall.mapper;

import com.moncozgc.mall.dto.HealthWikiWord;
import io.netty.util.internal.StringUtil;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by MoncozGC on 2023/2/14
 */
@Repository
public class HealthWikiWordMapper {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    private static final int MAX_RESULT_NUM = 100;

    public List<HealthWikiWord> queryHealthWikiWord(HealthWikiWord wikiWord) {
        if (StringUtil.isNullOrEmpty(wikiWord.getWord())) {
            return Collections.emptyList();
        }

        BoolQueryBuilder queryBase = QueryBuilders.boolQuery();
        queryBase.must(QueryBuilders.matchQuery("word", wikiWord.getWord()));

        if (wikiWord.getType() != null) {
            queryBase.must(QueryBuilders.termQuery("type", wikiWord.getType()));
        }

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withMaxResults(MAX_RESULT_NUM);
        nativeSearchQueryBuilder.withQuery(queryBase);

        SearchHits<HealthWikiWord> search = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), HealthWikiWord.class);
        return search.get().map(SearchHit::getContent).collect(Collectors.toList());
    }
}
