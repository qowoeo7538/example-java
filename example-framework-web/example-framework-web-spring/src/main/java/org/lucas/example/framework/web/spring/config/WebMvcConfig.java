package org.lucas.example.framework.web.spring.config;

import org.lucas.example.framework.web.spring.config.support.CustomAttributeMethodProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 配置业务线程池
     */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(8);
        threadPool.setMaxPoolSize(64);
        threadPool.setQueueCapacity(64);
        threadPool.setThreadNamePrefix("default-");
        threadPool.initialize();
        configurer.setTaskExecutor(threadPool);
    }

    /**
     * Spring mvc 参数处理
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new CustomAttributeMethodProcessor());
    }

    /**
     * 自定义异常处理，异常之后的回调
     *
     * @param resolvers
     */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {

    }
}
