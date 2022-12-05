package com.moncozgc.mall.common.utils;

import com.alibaba.druid.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 读取自定义配置文件中的值
 * yml配置信息可以通过注解直接获取. @Value("${xxx.xxx}") 或者 @ConfigurationProperties(prefix = "xxxx") https://blog.csdn.net/qq_35387940/article/details/106209485
 *
 * Created by pengqi on 2022/12/5
 */
public class PropertiesUtilTo {

    private static Logger log = LoggerFactory.getLogger(PropertiesUtil.class);
    private static Properties properties;

    static {
        String fileName = "task.properties";
        properties = new Properties();

        try {
            properties.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName)));
        } catch (IOException e) {
            log.error("配置文件读取异常", e);
        }
    }

    /**
     * 根据配置文件中的key获取value
     *
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        // 去除字符串前后的空格符号
        String value = properties.getProperty(key.trim());
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return value.trim();
    }

    /**
     * 根据配置文件中的key获取value, 当前获取不到值赋予默认值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getProperty(String key, String defaultValue) {
        String value = properties.getProperty(key.trim());
        if (StringUtils.isEmpty(value.trim())) {
            value = defaultValue;
        }
        return value.trim();
    }

    public static void main(String[] args) {
        System.out.println(getProperty("redis_host"));
    }
}
