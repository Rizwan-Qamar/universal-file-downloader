package com.agoda.core.api;

import com.agoda.DownloadManager;
import com.agoda.model.ResourceModel;
import com.agoda.protocols.AbstractFileHandler;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class BatchTask implements com.agoda.core.interfaces.BatchTask {
  private Logger log = LoggerFactory.getLogger(this.getClass());

  private ThreadPoolTaskExecutor asyncExecutor;

  // TODO Start all tasks
  // TODO Wait for all tasks to finish
  // TODO Update database

  @PostConstruct
  public void init() {

    log.debug("Running BatchTask init");
    final int corePoolSize = 20;

    /** Asynchronous thread executor configuration */
    this.asyncExecutor = new ThreadPoolTaskExecutor();

    this.asyncExecutor.setThreadNamePrefix("BatchTask-");
    this.asyncExecutor.setCorePoolSize(corePoolSize);
    this.asyncExecutor.initialize();

    log.debug("AsyncExecutor is: " + asyncExecutor);

    log.trace("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    log.trace("ThreadPollTaskExecutor configuration:");
    log.trace("MaxPoolSize = " + this.asyncExecutor.getMaxPoolSize());
    log.trace("CorePoolSize = " + this.asyncExecutor.getCorePoolSize());
    log.trace("QueueCapacity = " + this.asyncExecutor.getThreadPoolExecutor().getQueue().size());
    log.trace("KeepAlive = " + this.asyncExecutor.getKeepAliveSeconds());
    log.trace("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
  }

  @Async
  public void processTask(List<String> resourcePaths) {

    List<AbstractFileHandler> tasks = new ArrayList<>();
    try {
      tasks = getTask(resourcePaths);
      submitTasks(tasks);
    } catch (MalformedURLException ex) {
      ex.printStackTrace();
      log.error(String.format("There was an exception for: %s", ex.getMessage()));
    }
  }

  private List<AbstractFileHandler> getTask(List<String> resourcePaths)
      throws MalformedURLException {
    List<AbstractFileHandler> tasks = new ArrayList<>();
    for (String resource : resourcePaths) {
      log.debug("Making Task: " + resource);
      AbstractFileHandler abstractFileHandler = DownloadManager.getInstance(resource);
      abstractFileHandler.setResourceLocation(new ResourceModel(resource, "anonymous", "anonymous"));

      tasks.add(abstractFileHandler);
    }
    return tasks;
  }

  private void submitTasks(List<AbstractFileHandler> tasks) {
    for (AbstractFileHandler abstractFileHandler : tasks) {
      log.debug("Submitting Task: " + abstractFileHandler);
      asyncExecutor.submit(abstractFileHandler);
    }
  }
}
