package com.agoda.core.interfaces;

import java.net.MalformedURLException;
import java.util.List;

public interface FileHandlerManagement {

  /**
   * This method allows a list of resources to be downloaded.
   *
   * @param files is a list of URLs
   */
  void downloadFiles(List<String> files) throws MalformedURLException;
}
