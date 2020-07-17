package com.mbw.office.demo.web.ctl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Mabowen
 * @date 2020-07-17 15:10
 */
@Controller
public class IndexCtl {

    @GetMapping(value = {"","/","/index"})
    public String index() {
        return "index.html";
    }
}
