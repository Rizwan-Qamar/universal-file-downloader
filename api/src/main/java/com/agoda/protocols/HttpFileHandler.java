package com.agoda.protocols;

import com.agoda.model.ResourceModel;
import java.io.*;
import java.net.URL;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpFileHandler extends AbstractFileHandler {
  private Logger log = LoggerFactory.getLogger(this.getClass());

  private String downloadDir = "downloads";

  @Override
  public void init(Object object) {
    if (object instanceof String) {
      downloadDir = (String) object;
    }
  }

  @Override
  public ResourceModel call() throws IOException {

    URL dataUrl = new URL(getResourceModel().getUrl());
    String filePath = FilenameUtils.concat(downloadDir, FilenameUtils.getName(dataUrl.getPath()));
    File file = new File(filePath);

    try (InputStream inputStream = new BufferedInputStream(dataUrl.openStream());
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file, false))) {
      log.debug("HTTP PROTOCOL: File " + dataUrl.getPath() + " has started downloading.");
      saveFile(inputStream, outputStream);
      log.debug("HTTP PROTOCOL: File " + dataUrl.getPath() + " has been downloaded successfully.");
    } catch (IOException ex) {
      log.error(String.format("There was an exception for: %s", ex.getMessage()));
      deleteFile(file);
    }

    return getResourceModel();
  }
}
