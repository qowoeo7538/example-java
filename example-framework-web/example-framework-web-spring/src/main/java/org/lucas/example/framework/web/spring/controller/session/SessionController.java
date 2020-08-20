package org.lucas.example.framework.web.spring.controller.session;

import org.lucas.example.framework.web.spring.define.entity.User;
import org.lucas.example.framework.web.spring.define.vo.OrderVO;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.UUID;

/**
 * 注解 SessionAttributes：将model的属性存储到 session 中
 */
@RestController
@RequestMapping("/session")
@SessionAttributes("user")
public class SessionController {

    @ModelAttribute(name = "user")
    public User user() {
        User user = new User();
        user.setUserName("default");
        return user;
    }

    @ModelAttribute(name = "order")
    public OrderVO order() {
        OrderVO order = new OrderVO();
        order.setCcNumber(UUID.randomUUID().toString());
        return new OrderVO();
    }

    @PostMapping
    public String setUser(@ModelAttribute("user") User user) {
        return "index";
    }

    @GetMapping
    public String getUser(@SessionAttribute("user") User user) {
        return user.getUserName();
    }

    @GetMapping("/order")
    public String getOrder(@ModelAttribute("order") OrderVO order) {
        return order.getCcNumber();
    }
}
