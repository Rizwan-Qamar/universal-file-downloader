package com.agoda;

import com.agoda.protocols.AbstractFileHandler;
import com.agoda.protocols.FtpFileHandler;
import com.agoda.protocols.HttpFileHandler;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadManager {
  public static AbstractFileHandler getInstance(String resourcePath) throws MalformedURLException {
    URL url = new URL(resourcePath);
    String protocol = url.getProtocol().toUpperCase();

    switch (protocol) {
      case "HTTP":
      case "HTTPS":
        return new HttpFileHandler();
      case "FTP":
        return new FtpFileHandler();
      default:
        System.out.println("no match");
        throw new MalformedURLException("Protocol is not supported for the URL " + resourcePath);
    }
  }
}
