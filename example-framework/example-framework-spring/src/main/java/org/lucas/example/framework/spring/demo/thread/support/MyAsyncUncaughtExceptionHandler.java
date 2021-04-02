package org.lucas.example.framework.spring.demo.thread.support;

import org.junit.platform.commons.util.ExceptionUtils;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

public class MyAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
        System.out.println(ExceptionUtils.readStackTrace(throwable));
    }

}
