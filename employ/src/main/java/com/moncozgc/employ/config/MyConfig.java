package com.moncozgc.employ.config;

import com.moncozgc.employ.bean.Pet;
import com.moncozgc.employ.bean.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.el.ArrayELResolver;

/**
 * @Configuration: 高速SpringBoot这是一个配置类 == 配置文件
 *                  proxyBeanMethods: 代理Bean的方法
 *                  Full(proxyBeanMethods = true): 全配置, 保证每个@Bean方法被调用多少次返回的组件都是单实例的.
 *                      实践: 外部的每一次调用, SpringBoot每次都会去检查容器中是否存在依赖. 只有在配置类中有依赖项时推荐使用.
 *                  Lite(proxyBeanMethods = false): 轻量配置, 每个@Bean方法被调用多少次返回的组件都是新创建的.
 *                      优点: SpringBoot不会去容器中检测是否存在, 启动更快.
 *                      实践: 如果只是给容器中注册组件, 没有其他组件依赖. 可设置为False, 启动会更快
 * @Bean: 给容器中添加组件, 以方法名作为组件的ID, 返回类型就是组件类型. 返回的值, 就是该组件在容器中的实例.默认是单实例的
 *          组件的名称可以自定义, 不一定需要是方法的名称
 *
 * 1. 默认是单实例的
 * 2. MyConfig.java配置类本身也是组件
 * 3.
 *
 * @Import({User.class, ArrayELResolver.class}): 给容器中自动创建出这两个类型的组件
 *
 * Created by MoncozGC on 2022/5/18
 */
@Import({User.class, ArrayELResolver.class})
@Configuration(proxyBeanMethods = true)
public class MyConfig {
    /**
     * 外部无论对配置类中的这个组件注册方法调用多少次获取的都是之前注册容器中的单实例对象
     * @return
     */
    @Bean
    public User user01() {
        User zhanSan = new User("张三", 18);
        // 依赖与tomcatPet组件, 建议 proxyBeanMethods = true
        zhanSan.setPet(tomcatPet());
        return zhanSan;
    }

    @Bean("tom")
    public Pet tomcatPet() {
        return new Pet("tomcat");
    }
}
