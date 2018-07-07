package com.agoda.interfaces;

import com.agoda.model.ResourceModel;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;

public interface FileHandler extends Callable<ResourceModel> {

  void saveFile(InputStream inputStream, OutputStream outputStream) throws IOException;

  void deleteFile(File file);
}
