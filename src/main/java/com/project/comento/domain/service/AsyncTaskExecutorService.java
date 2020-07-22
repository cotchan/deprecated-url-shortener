package com.project.comento.domain.service;


import com.linecorp.armeria.common.util.ThreadFactories;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
public class AsyncTaskExecutorService extends ThreadPoolTaskExecutor {

    private static final String DEFAULT_EXECUTOR_NAME = "AsyncTaskExecutor";

    public AsyncTaskExecutorService(String _executorName, int coreSize, int maxSize) {
        assert coreSize > 0;
        assert maxSize > 0;

        String executorName = _executorName;
        if (Strings.isEmpty(executorName)) {
            log.warn("Executor does not have name replaced defaultName<{}>", DEFAULT_EXECUTOR_NAME);
            executorName = DEFAULT_EXECUTOR_NAME;
        }

        setCorePoolSize(coreSize);
        setMaxPoolSize(maxSize);
        setThreadFactory(ThreadFactories.newThreadFactory(executorName, true));
    }
}
