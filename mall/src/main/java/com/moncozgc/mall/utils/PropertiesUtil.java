package com.moncozgc.mall.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author wangzai
 * @version 1.0
 * @date 2022/2/16 10:32
 */
public class PropertiesUtil {
    public static Properties loadProperties(String... propertiesName) throws Exception {
        Properties properties = new Properties();
        for (String name : propertiesName) {
            InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
            properties.load(resourceAsStream);
        }
        return properties;
    }


    public static Properties loadLocalProperties(String dir, String... propertiesName) {
        Properties properties = new Properties();
        try {
            for (String name : propertiesName) {
                name = dir + "/" + name;
                File propertiesFile = new File(name);
                FileInputStream fileInputStream = new FileInputStream(propertiesFile);
                properties.load(fileInputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }
}
