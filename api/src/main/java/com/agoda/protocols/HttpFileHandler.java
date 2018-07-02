package com.agoda.protocols;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class HttpFileHandler extends AbstractFileHandler {

  @Override
  public String call() throws Exception {

    try (InputStream inputStream = new URL(getResourceLocation()).openStream();
        OutputStream outputStream = new FileOutputStream("path/file", false)) {}

    return null;
  }
}
