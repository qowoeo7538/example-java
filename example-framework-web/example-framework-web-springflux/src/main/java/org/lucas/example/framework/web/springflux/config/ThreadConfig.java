package org.lucas.example.framework.web.springflux.config;

import org.lucas.component.thread.task.ThreadPoolTaskExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class ThreadConfig {

    private Integer queueCapacity = 128;

    @Bean
    @Primary
    public ThreadPoolTaskExecutor standardThreadExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        return threadPool;
    }

    @Bean("threadPoolTaskExecutor")
    public org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor threadPool = new org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(2);
        threadPool.setMaxPoolSize(2);
        threadPool.setQueueCapacity(queueCapacity);
        threadPool.setThreadNamePrefix("serialize-");
        threadPool.initialize();
        return threadPool;
    }

}
