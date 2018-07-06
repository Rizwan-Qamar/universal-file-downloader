package com.agoda.core.api;

import com.agoda.core.interfaces.BatchTask;
import com.agoda.entities.Batch;
import com.agoda.repositories.BatchRepository;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
@ConfigurationProperties
public class BatchManagement implements com.agoda.core.interfaces.BatchManagement {
  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired private BatchTask batchTask;
  @Autowired private BatchRepository batchRepository;

  public static String[] schemes = {"http", "https", "ftp"};

  @Override
  public void downloadFiles(List<String> files) throws MalformedURLException {

    if (!validateUrl(files)) {
      //TODO Throw exception as one or more URLs were not valid
      throw new MalformedURLException("One or more URLs were not valid ");
    }

    Batch batch = getBatch(files);
    log.info("Saving batch info into the datebase");
    log.trace("Saving batch with info: " + String.valueOf(batch));
    batch = batchRepository.save(batch);

    log.debug("Start processing batch request");
    try {
      batchTask.processTask(batch);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }

    log.trace("Batch request was initiated");
    //TODO After all the batch has finished
  }

  @Override
  public void addBatch(Batch batch) {
    batchRepository.save(batch);
  }

  private Batch getBatch(List<String> urls) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss z");
    Batch batch = new Batch();
    batch.setCreatedAt(format.format(new Date()));
    batch.setUpdatedAt(format.format(new Date()));
    batch.getUrls().addAll(urls);
    return batch;
  }

  protected boolean validateUrl(List<String> resourcePaths) {
    UrlValidator urlValidator = new UrlValidator(schemes);
    for (String resource : resourcePaths) {
      if (urlValidator.isValid(resource)) {
        continue;
      }
      log.info(String.format("Not valid URL: %s", resource));
      return false;
    }
    return true;
  }
}