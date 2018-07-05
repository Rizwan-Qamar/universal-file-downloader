package com.agoda.protocols;

import com.agoda.model.ResourceModel;
import java.io.*;
import java.net.URL;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;

@RunWith(MockitoJUnitRunner.class)
public class HttpFileHandlerTest {

  @Test
  public void whenNotUseMockAnnotation_thenCorrect() throws Exception {
    //    InputStream mockInputStream =
    //        IOUtils.toInputStream("some test data for my input stream", "UTF-8");
    //
    //    HttpFileHandler httpFileHandler = PowerMockito.spy(new HttpFileHandler());
    //    httpFileHandler.setResourceLocation(
    //        new ResourceModel("https://c2.staticflickr.com/8/7151/6760135001_14c59a1490_o.jpg"));
    //
    //    PowerMockito.doReturn(mockInputStream).when(httpFileHandler, "getInputStream", anyObject());
    //
    //    //    PowerMockito.doReturn(mockInputStream).when(httpFileHandler, "isMethod");
    //
    //    httpFileHandler.call();

    //
    //
    //        mock.call()
    //        mockList.add("one");
    //        Mockito.verify(mockList).add("one");
    //        assertEquals(0, mockList.size());
    //
    //        Mockito.when(mockList.size()).thenReturn(100);
    //        assertEquals(100, mockList.size());
  }

  @Test
  public void whenInputIsCorrectOnCall_thenFileCreate() throws Exception {
    //    InputStream mockInputStream =
    //        IOUtils.toInputStream("some test data for my input stream", "UTF-8");
    //
    //
    //    URL url = PowerMockito.mock(URL.class);
    //    PowerMockito.whenNew(URL.class).withParameterTypes(String.class)
    //            .withArguments(Mockito.anyString()).thenReturn(url);

    URL f = new File("test_input" + "/input.txt").toURI().toURL();

    HttpFileHandler httpFileHandler = new HttpFileHandler();
    httpFileHandler.setResourceLocation(
        new ResourceModel(
            "https://archive.org/download/80MegapixelsCameraSampleImage/CF000891.jpg"));

    PowerMockito.whenNew(URL.class)
        .withParameterTypes(String.class)
        .withArguments(Mockito.anyString())
        .thenReturn(f);
    //    PowerMockito.doReturn(mockInputStream).when(httpFileHandler, "getInputStream", anyObject());
    //
    //    //    PowerMockito.doReturn(mockInputStream).when(httpFileHandler, "isMethod");
    //
    httpFileHandler.call();

    //
    //
    //        mock.call()
    //        mockList.add("one");
    //        Mockito.verify(mockList).add("one");
    //        assertEquals(0, mockList.size());
    //
    //        Mockito.when(mockList.size()).thenReturn(100);
    //        assertEquals(100, mockList.size());
  }

  @Test
  public void inputStreamSaveFile_thenCorrectOutStream() throws Exception {
    String str = "Hello World";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(str.getBytes());
    HttpFileHandler httpFileHandler = new HttpFileHandler();

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    httpFileHandler.saveFile(inputStream, baos);

    //create input stream from baos
    InputStream isFromFirstData = new ByteArrayInputStream(baos.toByteArray());
    httpFileHandler.saveFile(isFromFirstData, outputStream);

    Assert.assertTrue(contentEquals(inputStream, isFromFirstData));
  }

  private boolean contentEquals(InputStream input1, InputStream input2) throws IOException {
    if (!(input1 instanceof BufferedInputStream)) {
      input1 = new BufferedInputStream(input1);
    }
    if (!(input2 instanceof BufferedInputStream)) {
      input2 = new BufferedInputStream(input2);
    }

    int ch = input1.read();
    while (-1 != ch) {
      int ch2 = input2.read();
      if (ch != ch2) {
        return false;
      }
      ch = input1.read();
    }

    int ch2 = input2.read();
    return (ch2 == -1);
  }
}
