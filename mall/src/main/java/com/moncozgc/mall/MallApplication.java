package com.moncozgc.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetAddress;
import java.net.UnknownHostException;

@EnableScheduling // 定时任务执行功能注解
@EnableDiscoveryClient // 将微服务注册到服务组件种, 让注册中心能够发现，扫描到该服务
@EnableFeignClients // 配合@FeignClient注解使用, 调用其他服务接口
//@EnableGlobalMethodSecurity(prePostEnabled = true) // 判断用户对某个控制层的方法是否具有控制权限
@MapperScan(basePackages = "com.moncozgc.mall.mapper")
@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class}
        , scanBasePackages = "com.moncozgc.mall")
public class MallApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(MallApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String property = env.getProperty("server.servlet.context-path");
        String path = property == null ? "" : property;
        System.out.println(
                "\n\t" +
                        "----------------------------------------------------------\n\t" +
                        "Application Sailrui-Boot is running! Access URLs:\n\t" +
                        "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                        "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
                        "------------------------------------------------------------");
    }

}
