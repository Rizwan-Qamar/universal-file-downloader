package com.agoda.protocols;

import com.agoda.model.ResourceModel;
import java.io.*;
import java.net.URL;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpFileHandler extends AbstractFileHandler {
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

    FTPClient ftpClient = new FTPClient();
    try {
      ftpClient.connect(dataUrl.getHost());

      if (!getResourceModel().getUsername().isEmpty()
          && !getResourceModel().getPassword().isEmpty())
        ftpClient.login(getResourceModel().getUsername(), getResourceModel().getPassword());

      ftpClient.enterLocalPassiveMode();
      ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

      try (InputStream inputStream =
              ftpClient.retrieveFileStream(String.format("%s", dataUrl.getFile()));
          OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file, false))) {
        log.debug("FTP PROTOCOL: File " + dataUrl.getPath() + " has started downloading.");
        saveFile(inputStream, outputStream);
        boolean success = ftpClient.completePendingCommand();
        if (success) {
          log.debug(
              "FTP PROTOCOL: File " + dataUrl.getPath() + " has been downloaded successfully.");
        }
      } catch (Exception ex) {
        log.error(String.format("There was an exception for: %s", ex.getMessage()));
        deleteFile(file);
      }
    } finally {
      try {
        if (ftpClient.isConnected()) {
          ftpClient.logout();
          ftpClient.disconnect();
        }
      } catch (IOException ex) {
        log.error(String.format("There was an exception for: %s", ex.getMessage()));
      }
    }
    return getResourceModel();
  }
}
