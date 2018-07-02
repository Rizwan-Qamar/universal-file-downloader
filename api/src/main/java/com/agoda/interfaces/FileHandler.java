package com.agoda.interfaces;

import java.util.concurrent.Callable;

public interface FileHandler extends Callable<String> {

  void saveFile();
  //    public String openConnection() throws Exception;
}
