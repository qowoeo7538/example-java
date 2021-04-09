package org.lucas.example.framework.web.spring.controller.validation;

import org.lucas.component.thread.task.ThreadPoolTaskExecutor;
import org.lucas.example.common.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.validation.Valid;

@RestController
@RequestMapping("/validation")
public class ValidationController {

    @Autowired
    private ThreadPoolTaskExecutor executor;

    @PostMapping("/order")
    public DeferredResult<String> validationAsyncOrder(@Valid OrderVO request, Errors error) {
        DeferredResult<String> deferredResult = new DeferredResult<>();
        executor.execute(() -> {
            try {
                if (error.hasErrors()) {
                    deferredResult.setResult("validation error.");
                }
                // 执行异步处理
                Thread.sleep(3000);
                // 设置结果
                deferredResult.setResult("validation ok.");
                System.out.println("---- end validation ----");
            } catch (final Exception e) {
                e.printStackTrace();
                deferredResult.setErrorResult("validation error.");
            }
        });
        return deferredResult;
    }

    @GetMapping("/order")
    public String validationOrder(@Valid OrderVO request, Errors error) {
        if (error.hasErrors()) {
            return "validation error.";
        }
        return "validation ok.";
    }

}
