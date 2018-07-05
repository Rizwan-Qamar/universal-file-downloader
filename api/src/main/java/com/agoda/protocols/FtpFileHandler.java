package com.agoda.protocols;

import java.io.*;
import java.net.URL;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpFileHandler extends AbstractFileHandler {
  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Override
  public String call() throws IOException {

    URL dataUrl = new URL(getResourceLocation().getUrl());
    String filePath = FilenameUtils.concat("downloads", FilenameUtils.getName(dataUrl.getPath()));
    File file = new File(filePath);

    // String serverAddress = "www.ftpserveraddress.com"; // ftp server address
    final int port = 21; // ftp uses default port Number 21
    String username = "xyz"; // username of ftp server
    String password = "xyz"; // password of ftp server

    FTPClient ftpClient = new FTPClient();
    try {
      ftpClient.connect(dataUrl.getHost());
      ftpClient.login(getResourceLocation().getUsername(), getResourceLocation().getPassword());
      ftpClient.enterLocalPassiveMode();
      ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

      try (InputStream inputStream =
              ftpClient.retrieveFileStream(String.format("%s", dataUrl.getFile()));
          OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file, false))) {

        saveFile(inputStream, outputStream);
        boolean success = ftpClient.completePendingCommand();
        if (success) {
          log.debug("FTP PROTOCOL: File " + dataUrl.getPath() + " has been downloaded successfully.");
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
    return filePath;
  }
}
