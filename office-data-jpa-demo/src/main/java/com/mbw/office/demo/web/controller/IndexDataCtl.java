package com.mbw.office.demo.web.controller;

import com.mbw.office.common.response.ResponseResults;
import com.mbw.office.demo.web.controller.base.BaseDataCtl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mabowen
 * @date 2020-07-07 15:07
 */
@Slf4j
@RestController
@RequestMapping("/index")
public class IndexDataCtl extends BaseDataCtl {

    @GetMapping("user/{userId}")
    public ResponseResults getUserById(@PathVariable("userId") Long userId) {
        try {
            return ResponseResults.newSuccess()
                    .setData(null);
        } catch (Exception e) {
            return ResponseResults.newFailed()
                    .setMessage(e.getMessage());
        }
    }

    @GetMapping("users")
    public ResponseResults listUsers() {
        try {
            return ResponseResults.newSuccess()
                    .setData(null);
        } catch (Exception e) {
            return ResponseResults.newFailed()
                    .setMessage(e.getMessage());
        }
    }
}
