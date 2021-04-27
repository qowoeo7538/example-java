package org.lucas.example.framework.web.spring.controller.data;

import org.lucas.example.common.entity.User;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dateMapper")
public class DateMapperController {

    @PostMapping("/user")
    public String dateMapper(User user, Errors error) {
        return "validation ok.";
    }

}
