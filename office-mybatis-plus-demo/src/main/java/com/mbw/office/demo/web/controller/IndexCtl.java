package com.mbw.office.demo.web.controller;

import com.mbw.office.demo.web.controller.base.BaseCtl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Mabowen
 * @date 2020-07-01 20:09
 */
@Controller
public class IndexCtl extends BaseCtl {

    @GetMapping(value = {"","/","/index"})
    public String index(Model model) {
        return "index.html";
    }
}
