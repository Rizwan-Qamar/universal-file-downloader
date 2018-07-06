package com.agoda.core.interfaces;

import com.agoda.entities.Batch;
import java.net.MalformedURLException;

public interface BatchTask {
  void processTask(Batch resourcePaths) throws MalformedURLException;
}
