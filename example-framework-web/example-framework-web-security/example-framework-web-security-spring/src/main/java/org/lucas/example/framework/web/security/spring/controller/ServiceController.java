package org.lucas.example.framework.web.security.spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
public class ServiceController {

    @RequestMapping("/test")
    public String test() {
        return "success";
    }

}
