package com.moncozgc.employ.controlle;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @RestController: 是Controller和ResponseBody的合体
 * @Controller: 控制请求
 * @ResponseBody: 返回给浏览器的数据
 * @RequestMapping: 映射请求
 * Created by MoncozGC on 2022/5/18
 */

@RestController
public class HelloWordContorlle {

    @RequestMapping("/hello")
    public String handle01() {
        return "Hello Spring Boot 2!";
    }
}
