package com.moncozgc.mall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 *
 * Created by MoncozGC on 2022/11/21
 */
@Configuration
@MapperScan("com.moncozgc.mall.mbg.mapper")
public class MyBatisConfig {
}
