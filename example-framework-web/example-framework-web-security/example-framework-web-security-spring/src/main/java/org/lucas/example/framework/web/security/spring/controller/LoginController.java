package org.lucas.example.framework.web.security.spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @RequestMapping("/login")
    public String test() {
        return "success";
    }

}
