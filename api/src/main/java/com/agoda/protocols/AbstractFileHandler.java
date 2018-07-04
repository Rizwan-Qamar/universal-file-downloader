package com.agoda.protocols;

import com.agoda.interfaces.FileHandler;
import com.agoda.model.ResourceModel;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractFileHandler implements FileHandler {
  private Logger log = LoggerFactory.getLogger(this.getClass());

  private ResourceModel resourceLocation;

  @Override
  public void saveFile(InputStream inputStream, OutputStream outputStream) throws IOException {

    byte[] buff = new byte[8 * 1024 * 1024]; //8KB

    int n = 0;
    while ((n = inputStream.read(buff)) >= 0) {
      outputStream.write(buff, 0, n);
    }
  }

  @Override
  public void deleteFile(File file) {
    log.debug("Deleting file at path: " + file.getAbsolutePath());

    file.delete();
  }

  /** Returns the path of saved file */
  public abstract String call() throws IOException;

  public ResourceModel getResourceLocation() {
    return resourceLocation;
  }

  public void setResourceLocation(ResourceModel resourceLocation) {
    this.resourceLocation = resourceLocation;
  }
}
