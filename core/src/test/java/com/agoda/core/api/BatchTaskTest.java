package com.agoda.core.api;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BatchTaskTest.class)
public class BatchTaskTest {

  @InjectMocks private BatchTask batchTask;

  @Mock private ThreadPoolTaskExecutor threadPoolTaskExecutor;

  @Before
  public void init() throws Exception {
    MockitoAnnotations.initMocks(this);
    whenNew(ThreadPoolTaskExecutor.class).withNoArguments().thenReturn(threadPoolTaskExecutor);
  }

  @Test(expected = MalformedURLException.class)
  public void processTaskMalformedURLException() throws MalformedURLException {
    List<String> urls = new ArrayList<>();
    urls.add("http://archive.org/download/80MegapixelsCameraSampleImage/CF000891.jpg");
    urls.add("htt://archive.org/download/80MegapixelsCameraSampleImage/CF000891.jpg");
    batchTask.processTask(urls);
  }

  @Test
  public void processTaskCompleteFlow() throws MalformedURLException {

    List<String> urls = new ArrayList<>();
    urls.add("http://archive.org/download/80MegapixelsCameraSampleImage/CF000891.jpg");
    urls.add("https://archive.org/download/80MegapixelsCameraSampleImage/CF000891.jpg");
    urls.add("http://archive.org/download/80MegapixelsCameraSampleImage/CF000891.jpg");
    urls.add("ftp://archive.org/download/80MegapixelsCameraSampleImage/CF000891.jpg");
    batchTask.processTask(urls);
  }
}
