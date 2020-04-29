package org.lucas.example.framework.web.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class ThreadConfig {

    private Integer coreSize = 32;

    private Integer maxSize = 64;

    private Integer queueCapacity = 128;

    @Bean
    @Primary
    public ThreadPoolTaskExecutor defaultExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(coreSize);
        threadPool.setMaxPoolSize(maxSize);
        threadPool.setQueueCapacity(queueCapacity);
        threadPool.setThreadNamePrefix("default-");
        threadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPool.initialize();
        return threadPool;
    }

    @Bean("serializeExecutor")
    public ThreadPoolTaskExecutor serializeExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(2);
        threadPool.setMaxPoolSize(2);
        threadPool.setQueueCapacity(queueCapacity);
        threadPool.setThreadNamePrefix("serialize-");
        threadPool.initialize();
        return threadPool;
    }

}
