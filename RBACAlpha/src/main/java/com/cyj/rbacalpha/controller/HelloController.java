package com.cyj.rbacalpha.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    @PreAuthorize("hasAnyAuthority('manage', 'normal')")
    public String hello() {
        return "Hello";
    }

    @GetMapping("/deleteUser")
    @PreAuthorize("hasAuthority('manage')")
    public String deleteUser() {
        return "delete user";
    }

    @GetMapping("/publish")
    @PreAuthorize("hasAuthority('normal')")
    public String publish() {
        return "publish note";
    }

}
