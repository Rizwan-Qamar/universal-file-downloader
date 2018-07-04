package com.agoda.system;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/*
This class enable the entire application to use methods annotated with @Async
The Executor, which will be used with @Async methods, can be configured here.
 */
@Configuration
@EnableAsync
public class AsyncExecutorConfig extends AsyncConfigurerSupport {

  @Override
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(20);
    executor.setMaxPoolSize(25);
    executor.setQueueCapacity(50);
    executor.setThreadNamePrefix("UFDAsyncTask-");
    executor.initialize();
    return executor;
  }
}
