package com.agoda.interfaces;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;

public interface FileHandler extends Callable<String> {

  void saveFile(InputStream inputStream, OutputStream outputStream);

  void deleteFile(File file);
}
