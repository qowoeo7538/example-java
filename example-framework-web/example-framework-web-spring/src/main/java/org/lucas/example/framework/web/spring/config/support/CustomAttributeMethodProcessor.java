package org.lucas.example.framework.web.spring.config.support;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

public class CustomAttributeMethodProcessor extends ServletModelAttributeMethodProcessor {

    public CustomAttributeMethodProcessor() {
        super(true);
    }

    @Override
    protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {
        //解决--限制了list最大只能增长到256
        binder.setAutoGrowCollectionLimit(2500);
        super.bindRequestParameters(binder, request);
        bindModuleId(binder);
    }

    /**
     * 参数处理
     *
     * @param binder
     */
    private void bindModuleId(WebDataBinder binder) {
        /*if (binder.getTarget() instanceof Req) {
            Req req = (Req) binder.getTarget();
            Long userId = SecurityUtils.getLoginUser().getUser().getUserId();
            req.setCurrentUserId(String.valueOf(userId));
        }*/
    }
}
