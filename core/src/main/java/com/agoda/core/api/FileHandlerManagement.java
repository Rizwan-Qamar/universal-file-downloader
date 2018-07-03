package com.agoda.core.api;

import com.agoda.protocols.AbstractFileHandler;
import com.agoda.protocols.HttpFileHandler;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class FileHandlerManagement implements com.agoda.core.interfaces.FileHandlerManagement {
  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Override
  public void downloadFiles(List<String> files) {
    //TODO Do validation for the URLS for the formats

    for (String file : files) {
      AbstractFileHandler abstractFileHandler = new HttpFileHandler();
      abstractFileHandler.setResourceLocation(file);
      try {
        abstractFileHandler.call();
      } catch (IOException e) {
        e.printStackTrace();
        log.error(
                String.format(
                        "There was an exception for: %s",

                        e.getMessage()));
      }
    }
  }
}
