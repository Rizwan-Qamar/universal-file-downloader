package com.agoda.protocols;

import com.agoda.interfaces.FileHandler;

public abstract class AbstractFileHandler implements FileHandler {
  private String resourceLocation;

  @Override
  public void saveFile() {}

  public String getResourceLocation() {
    return resourceLocation;
  }

  public void setResourceLocation(String resourceLocation) {
    this.resourceLocation = resourceLocation;
  }
}
