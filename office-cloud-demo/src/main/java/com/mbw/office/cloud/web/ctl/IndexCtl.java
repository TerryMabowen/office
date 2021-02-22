package com.mbw.office.cloud.web.ctl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2021-02-22 15:23
 */
@Controller
public class IndexCtl {

    @GetMapping({"", "/", "/index", "/index.html"})
    public String index() {
        return "index.html";
    }
}
