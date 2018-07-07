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

    File outputFile = new File("test-output" + "/input_out.txt");
    AbstractFileHandler abstractFileHandler = Mockito.spy(AbstractFileHandler.class);

    try (InputStream inputStream =
            new File("test_input" + "/input.txt").toURI().toURL().openStream();
        OutputStream outputStream = new FileOutputStream(outputFile, false)) {
      abstractFileHandler.saveFile(inputStream, outputStream);
    }
    Assert.assertTrue(outputFile.exists());
    outputFile.delete();
  }
}
