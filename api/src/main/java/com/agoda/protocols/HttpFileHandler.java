package com.agoda.protocols;

import java.io.*;
import java.net.URL;
import org.apache.commons.io.FilenameUtils;

public class HttpFileHandler extends AbstractFileHandler {

  @Override
  public String call() throws IOException {

    URL dataUrl = new URL(getResourceLocation());
    String filePath = FilenameUtils.concat("downloads", FilenameUtils.getName(dataUrl.getPath()));
    File file = new File(filePath);

    try (InputStream inputStream = dataUrl.openStream();
        OutputStream outputStream = new FileOutputStream(file, false)) {

      saveFile(inputStream, outputStream);

    } catch (IOException ex) {
      deleteFile(file);
    }

    return filePath;
  }
}
