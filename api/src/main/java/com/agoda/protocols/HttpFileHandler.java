package com.agoda.protocols;

import java.io.*;
import java.net.URL;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpFileHandler extends AbstractFileHandler {
  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Override
  public String call() throws IOException {

    URL dataUrl = new URL(getResourceLocation().getUrl());
    String filePath = FilenameUtils.concat("downloads", FilenameUtils.getName(dataUrl.getPath()));
    File file = new File(filePath);

    try (InputStream inputStream = new BufferedInputStream(dataUrl.openStream());
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file, false))) {

      saveFile(inputStream, outputStream);

    } catch (IOException ex) {
      log.error(String.format("There was an exception for: %s", ex.getMessage()));
      deleteFile(file);
    }

    return filePath;
  }
}
