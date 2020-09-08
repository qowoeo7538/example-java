package org.lucas.example.framework.web.security.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
public class ServiceController {

    @GetMapping("/test")
    public String test() {
        return "success";
    }

    @RequestMapping("/admin")
    public String admin() {
        return "success";
    }

    @RequestMapping("/user")
    public String user() {
        return "success";
    }

}
