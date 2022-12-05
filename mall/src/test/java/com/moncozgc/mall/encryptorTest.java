package com.moncozgc.mall;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by MoncozGC on 2022/12/5
 */
@SpringBootTest
public class encryptorTest {

    @Autowired
    StringEncryptor encryptor;

    /**
     * 使用StringEncryptor对配置文件进行加密解密.
     * 加密过程如下代码.
     * 解密过程:
     * 1. 在配置文件中配置: ENC(加密的信息), 及配置加密的密钥: jasypt.encryptor.password: xxxx
     * 2. 加密的密钥不写在配置文件中的方式:
     *      1. jar包启动方式: java -jar -Djasypt.encryptor.password=xxx xxx.jar
     *      2. IDEA启动方式: VM options中配置: -Djasypt.encryptor.password=xxx
     */
    @Test
    void contextLoads() {

        String dbUrl = encryptor.encrypt("jdbc:mysql://localhost:3306/mall?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&zeroDateTimeBehavior=convertToNull");

        System.out.println("dbUrl: " + dbUrl);

        String username = encryptor.encrypt("root");

        System.out.println(username);

        String password = encryptor.encrypt("root");

        System.out.println(password);

    }


    @Test
    void conversion() {
        System.out.println(System.getProperty("user.dir"));
    }
}
