package com.agoda.protocols;

import com.agoda.exceptions.FileHandlerException;
import com.agoda.model.ResourceModel;
import java.io.*;
import java.net.URL;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpFileHandler extends AbstractFileHandler {
  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Override
  public ResourceModel call() throws IOException, FileHandlerException {

    URL dataUrl = new URL(getResourceModel().getUrl());
    String filePath = FilenameUtils.concat(getDownloadDir(), getResourceModel().getResourceName());
    File file = new File(filePath);

    try (InputStream inputStream = new BufferedInputStream(dataUrl.openStream());
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file, false))) {
      log.debug(
          "HTTP PROTOCOL: File "
              + FilenameUtils.getName(getResourceModel().getUrl())
              + " has started downloading.");
      saveFile(inputStream, outputStream);
      log.debug(
          "HTTP PROTOCOL: File "
              + FilenameUtils.getName(getResourceModel().getUrl())
              + " has been downloaded successfully.");
    } catch (IOException ex) {
      log.error(String.format("There was an exception for: %s", ex.getMessage()));
      deleteFile(file);
      genericExceptionHandler(ex);
    }

    return getResourceModel();
  }
}
