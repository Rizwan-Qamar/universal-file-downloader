package com.agoda.protocols;

import com.agoda.exceptions.FileHandlerException;
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

  private ResourceModel resourceModel;
  private String downloadDir;

  public void init(Object object) {
    if (object instanceof String) {
      downloadDir = (String) object;
    }
  }

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

  /** Returns the resource model */
  public abstract ResourceModel call() throws FileHandlerException, IOException;

  public ResourceModel getResourceModel() {
    return resourceModel;
  }

  public void setResourceModel(ResourceModel resourceModel) {
    this.resourceModel = resourceModel;
  }

  public String getDownloadDir() {
    File dir = new File(downloadDir);
    if (!dir.exists()) {
      dir.mkdirs();
    }
    return downloadDir;
  }

  public void setDownloadDir(String path) {
    this.downloadDir = path;
  }

  public void genericExceptionHandler(Exception e) throws FileHandlerException {
    throw new FileHandlerException(
        "There was an exception for: "
            + getResourceModel().getUrl()
            + ". Caused by: "
            + e.getMessage(),
        e,
        getResourceModel());
  }
}
