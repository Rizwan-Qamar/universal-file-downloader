package com.agoda.core.api;

import com.agoda.DownloadManager;
import com.agoda.entities.Batch;
import com.agoda.entities.BatchItem;
import com.agoda.model.ResourceModel;
import com.agoda.protocols.AbstractFileHandler;
import com.agoda.repositories.BatchRepository;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired private BatchRepository batchRepository;

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
    List<Future<ResourceModel>> futures = new ArrayList<>();
    List<AbstractFileHandler> tasks;
    try {
      tasks = getTask(batch.getBatchItems());
      futures = submitTasks(tasks);
    } catch (MalformedURLException ex) {
      ex.printStackTrace();
      log.error(String.format("There was an exception for: %s", ex.getMessage()));
      throw ex;
    }

    log.debug("Waiting for tasks");
    for (Future<ResourceModel> future : futures) {

      try {
        ResourceModel resourceModel = future.get();
        log.debug(
            "Identifier: "
                + resourceModel.getIdentifier()
                + " File "
                + resourceModel.getUrl()
                + " has been downloaded successfully.");
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {

      }
    }

    cleanUp();

    log.debug("Updating status of batch: " + batch.getId());

    //TODO After all the batch has finished Update status of Batch to completed and update status of all Item to completed or Exception
  }

  private List<AbstractFileHandler> getTask(List<BatchItem> resourcePaths)
      throws MalformedURLException {
    List<AbstractFileHandler> tasks = new ArrayList<>();
    for (BatchItem batchItem : resourcePaths) {
      log.debug("Making Task: " + batchItem);
      AbstractFileHandler abstractFileHandler =
          DownloadManager.getInstance(batchItem.getResourceLocation());
      abstractFileHandler.init(downloadDir);
      abstractFileHandler.setResourceModel(
          new ResourceModel(
              batchItem.getId(),
              batchItem.getResourceLocation(),
              genericUsername,
              genericPassword));

      tasks.add(abstractFileHandler);
    }
    return tasks;
  }

  private List<Future<ResourceModel>> submitTasks(List<AbstractFileHandler> tasks) {
    List<Future<ResourceModel>> futures = new ArrayList<>();
    for (AbstractFileHandler abstractFileHandler : tasks) {
      log.debug("Submitting Task: " + abstractFileHandler);
      futures.add(asyncExecutor.submit(abstractFileHandler));
    }
    return futures;
  }

  @PreDestroy
  public void cleanUp() {
    if (asyncExecutor != null) {
      asyncExecutor.shutdown();
    }
  }
}
