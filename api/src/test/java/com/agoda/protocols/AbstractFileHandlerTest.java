package com.agoda.protocols;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class AbstractFileHandlerTest {

  @Test
  public void whenSaveFile_thenFileIsCreate() throws Exception {

    InputStream inputStream = new File("test_input" + "/input.txt").toURI().toURL().openStream();
    File outputFile = new File("test-output" + "/input_out.txt");
    OutputStream outputStream = new FileOutputStream(outputFile, false);

    AbstractFileHandler abstractFileHandler = Mockito.spy(AbstractFileHandler.class);

    abstractFileHandler.saveFile(inputStream, outputStream);

    Assert.assertTrue(outputFile.exists());
    outputFile.delete();
  }
}
