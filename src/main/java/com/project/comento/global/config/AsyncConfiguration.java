package com.project.comento.global.config;

import com.project.comento.domain.service.AsyncTaskExecutorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
public class AsyncConfiguration implements AsyncConfigurer {

    @Bean(name = "asyncExecutor")
    public Executor asyncExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    @Bean(name = "armeriaAsyncTaskExecutor")
    public Executor armeriaAsyncTaskExecutor() {
        return new AsyncTaskExecutorService("armeriaAsyncTaskExecutor", 10, 10);
    }
}
