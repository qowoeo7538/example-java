package org.lucas.example.framework.web.spring.controller.session;

import org.lucas.example.common.entity.User;
import org.lucas.example.common.vo.OrderVO;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.util.UUID;

/**
 * 注解 @SessionAttributes：将model的属性存储到 session 中,如果方法体没有标注@SessionAttributes("xxx")，那么scope为request，如果标注了，那么 scope为session
 * <p>
 * 注解 @ModelAttribute：注解的方法会在此controller每个方法执行前被执行
 */
@RestController
@RequestMapping("/session")
@SessionAttributes("user")
public class SessionController {

    /**
     * Model 由返回类型隐含表示，Model 属性对象就是方法的返回值。
     */
    @ModelAttribute(name = "user")
    public User user() {
        User user = new User();
        user.setUserName("default");
        return user;
    }

    @ModelAttribute
    public void order(@RequestParam(required = false) String order, Model model) {
        if (StringUtils.hasLength(order)) {
            OrderVO orderVO = new OrderVO();
            orderVO.setCcNumber(UUID.randomUUID().toString().replace("-", ""));
            model.addAttribute("order", orderVO);
        }
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

    /**
     * session 重置
     *
     * @param model
     * @param sessionStatus
     * @return
     */
    @GetMapping("/complete")
    public String getParam(Model model, SessionStatus sessionStatus) {
        // session 重置
        sessionStatus.setComplete();
        return "ok";
    }

}
