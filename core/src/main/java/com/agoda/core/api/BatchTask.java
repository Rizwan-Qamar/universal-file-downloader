package com.agoda.core.api;

import com.agoda.DownloadManager;
import com.agoda.entities.Batch;
import com.agoda.model.ResourceModel;
import com.agoda.protocols.AbstractFileHandler;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@ConfigurationProperties
public class BatchTask implements com.agoda.core.interfaces.BatchTask {
  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Value("${ufd.core.api.downloadLocation:downloads}")
  private String downloadDir;

  @Value("${ufd.core.api.generic.username}")
  private String genericUsername;

  @Value("${ufd.core.api.generic.password}")
  private String genericPassword;

  private ThreadPoolTaskExecutor asyncExecutor;

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

    log.debug("AsyncExecutor is: " + asyncExecutor.getThreadNamePrefix());

    log.trace("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    log.trace("ThreadPollTaskExecutor configuration:");
    log.trace("MaxPoolSize = " + this.asyncExecutor.getMaxPoolSize());
    log.trace("CorePoolSize = " + this.asyncExecutor.getCorePoolSize());
    log.trace("QueueCapacity = " + this.asyncExecutor.getThreadPoolExecutor().getQueue().size());
    log.trace("KeepAlive = " + this.asyncExecutor.getKeepAliveSeconds());
    log.trace("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
  }

  @Async
  public void processTask(Batch batch) throws MalformedURLException {

    log.info("Batch received: " + batch);
    List<AbstractFileHandler> tasks;
    try {
      tasks = getTask(batch.getUrls());
      submitTasks(tasks);
    } catch (MalformedURLException ex) {
      ex.printStackTrace();
      log.error(String.format("There was an exception for: %s", ex.getMessage()));
      throw ex;
    }
  }

  private List<AbstractFileHandler> getTask(List<String> resourcePaths)
      throws MalformedURLException {
    List<AbstractFileHandler> tasks = new ArrayList<>();
    for (String resource : resourcePaths) {
      log.debug("Making Task: " + resource);
      AbstractFileHandler abstractFileHandler = DownloadManager.getInstance(resource);
      abstractFileHandler.init(downloadDir);
      abstractFileHandler.setResourceLocation(
          new ResourceModel(resource, genericUsername, genericPassword));

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
