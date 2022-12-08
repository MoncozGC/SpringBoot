package com.moncozgc.mall.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器手动配置类, 自定义一个适配器
 * 配置类, 在项目启动的时候自动执行
 *
 * SpringBoot 通过实现HandlerInterceptor接口实现拦截器，通过实现WebMvcConfigurer接口实现一个配置类，在配置类中注入拦截器，最后再通过 @Configuration 注解注入配置.
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }


    /**
     * 将拦截器注入到适配器中
     *
     * @param registry 拦截器列表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] excludePath = {"/*.js", "/*.css", "/*.svg", "/*.pdf", "/*.jpg", "/*.png", "/*.ico", "/*.html", "/html/**", "/js/**", "/css/**", "/images/**"};
        registry.addInterceptor(authenticationInterceptor())
                .excludePathPatterns(excludePath) // 配置不需要拦截的路径. 也可以使用自定义@PassToken注解跳过
                .addPathPatterns("/**"); // 设置拦截路径, 可以是多个字符串或者直接传入一个数组
    }

}