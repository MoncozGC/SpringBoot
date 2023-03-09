package com.moncozgc.employ.controlle;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页
 *
 * Created by MoncozGC on 2022/11/21
 */
@RestController
public class IndexController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("name", "employ");
        return "employ/list";
    }

}
