package com.agoda.core.interfaces;

import java.net.MalformedURLException;
import java.util.List;

public interface BatchTask {
  void processTask(List<String> resourcePaths) throws MalformedURLException;
}
