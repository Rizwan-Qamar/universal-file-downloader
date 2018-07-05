package com.agoda.core.api;

import com.agoda.core.interfaces.BatchTask;
import java.util.List;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class FileHandlerManagement implements com.agoda.core.interfaces.FileHandlerManagement {
  private Logger log = LoggerFactory.getLogger(this.getClass());

  public static String[] schemes = {"http", "https", "ftp"};

  @Autowired private BatchTask batchTask;

  @Override
  public void downloadFiles(List<String> files) {

    if (!validateUrl(files)) {
      //TODO Throw exception as one or more URLs were not valid
    }

    log.info("Save batch info into the datebase");
    //TODO Save the batch to database

    log.trace("Start processing batch request");
    //TODO Run the batch in Async

    batchTask.processTask(files);

    log.trace("Batch request was initiated");
    //TODO After all the batch has finished
  }

  private boolean validateUrl(List<String> resourcePaths) {
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