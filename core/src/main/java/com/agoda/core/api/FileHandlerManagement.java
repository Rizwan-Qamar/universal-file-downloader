package com.agoda.core.api;

import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class FileHandlerManagement implements com.agoda.core.interfaces.FileHandlerManagement {

  @Override
  public void downloadFiles(List<String> files) {
    //TODO Do validation for the URLS for the formats

  }
}
