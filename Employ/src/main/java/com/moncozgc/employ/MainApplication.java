package com.moncozgc.employ;

import com.moncozgc.employ.bean.Pet;
import com.moncozgc.employ.bean.User;
import com.moncozgc.employ.config.MyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @SpringBootApplication: 这是一个SpringBoot应用程序
 * scanBasePackages: 将扫描的包文件范围扩大
 * Created by MoncozGC on 2022/5/18
 */
@SpringBootApplication(scanBasePackages = "com.moncozgc")
public class MainApplication {
    public static void main(String[] args) {
        // 返回IOC容器
        ConfigurableApplicationContext run = SpringApplication.run(MainApplication.class);
        String[] names = run.getBeanDefinitionNames();
        for (String name : names) {
            // 打印容器中的组件名称
            System.out.println(name);
        }
        // 从容器中获取组件, 判断容器中的组件是不是单实例的
        Pet tom01 = run.getBean("tom", Pet.class);
        Pet tom02 = run.getBean("tom", Pet.class);
        System.out.println("组件: " + (tom01 == tom02));

        MyConfig bean = run.getBean(MyConfig.class);
        System.out.println(bean);

        // 如果@Configuration(proxyBeanMethods = true)代理对象调用方法. SpringBoot总会检查这个组件是否在容器中, 如果有就不会创建.
        // 保持单实例
        User user = bean.user01();
        User user2 = bean.user01();
        System.out.println(user == user2);
    }
}
