package com.agoda.core.interfaces;

import com.agoda.entities.Batch;
import java.net.MalformedURLException;
import java.util.List;

public interface BatchManagement {

  /**
   * This method allows a list of resources to be downloaded.
   *
   * @param files is a list of URLs
   */
  void downloadFiles(List<String> files) throws MalformedURLException;

  void addBatch(Batch batch);

  Iterable<Batch> query();

  boolean isApproved(String id);

  boolean isRejected(String id);
}
