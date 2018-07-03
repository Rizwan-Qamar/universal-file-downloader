package com.agoda.protocols;

import com.agoda.interfaces.FileHandler;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractFileHandler implements FileHandler {
  private Logger log = LoggerFactory.getLogger(this.getClass());

  private String resourceLocation;

  @Override
  public void saveFile(InputStream inputStream, OutputStream outputStream) {}

  @Override
  public void deleteFile(File file) {
    log.debug("Deleting file at path: " + file.getAbsolutePath());

    file.delete();
  }

  /** Returns the path of saved file */
  public abstract String call() throws IOException;

  public String getResourceLocation() {
    return resourceLocation;
  }

  public void setResourceLocation(String resourceLocation) {
    this.resourceLocation = resourceLocation;
  }
}
