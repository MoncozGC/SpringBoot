package com.moncozgc.mall.dto;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 为elasticsearch生成文档dto
 *
 * Created by MoncozGC on 2023/2/14
 */
@Data
@Document(indexName = "health_wiki_word")
public class HealthWikiWord {
    @Field(name = "id", type = FieldType.Long, index = false)
    private Long id;
    @Field(name = "type", type = FieldType.Integer)
    private Integer type;
    @Field(name = "word", type = FieldType.Text, analyzer = "ik_smart")
    private String word;
    @Field(name = "content", type = FieldType.Keyword, index = false)
    private String content;
    @Field(name = "update_time", type = FieldType.Date, index = false,
            store = true, format = DateFormat.basic_date_time)
    private String updateTime;
}
