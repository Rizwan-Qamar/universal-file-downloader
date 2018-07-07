package com.agoda.core.api;

import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import com.agoda.entities.Batch;
import com.agoda.entities.BatchItem;
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
  @Mock private Batch batch;

  @Before
  public void init() throws Exception {
    MockitoAnnotations.initMocks(this);
    whenNew(ThreadPoolTaskExecutor.class).withNoArguments().thenReturn(threadPoolTaskExecutor);
  }

  @Test(expected = MalformedURLException.class)
  public void processTaskMalformedURLException() throws MalformedURLException {

    List<BatchItem> batchItems = new ArrayList<>();
    BatchItem batchItem = new BatchItem();
    batchItem.setResourceLocation(
        "http://archive.org/download/80MegapixelsCameraSampleImage/CF000891.jpg");
    batchItems.add(batchItem);

    batchItem = new BatchItem();
    batchItem.setResourceLocation(
        "htt://archive.org/download/80MegapixelsCameraSampleImage/CF000891.jpg");
    batchItems.add(batchItem);

    when(batch.getBatchItems()).thenReturn(batchItems);
    batchTask.processTask(batch);
  }

  @Test
  public void processTaskCompleteFlow() throws MalformedURLException {

    List<BatchItem> batchItems = new ArrayList<>();
    BatchItem batchItem = new BatchItem();
    batchItem.setResourceLocation(
        "http://archive.org/download/80MegapixelsCameraSampleImage/CF000891.jpg");
    batchItems.add(batchItem);

    batchItem = new BatchItem();
    batchItem.setResourceLocation(
        "https://archive.org/download/80MegapixelsCameraSampleImage/CF000891.jpg");
    batchItems.add(batchItem);

    batchItem = new BatchItem();
    batchItem.setResourceLocation(
        "http://archive.org/download/80MegapixelsCameraSampleImage/CF000891.jpg");
    batchItems.add(batchItem);

    batchItem = new BatchItem();
    batchItem.setResourceLocation(
        "ftp://archive.org/download/80MegapixelsCameraSampleImage/CF000891.jpg");
    batchItems.add(batchItem);

    when(batch.getBatchItems()).thenReturn(batchItems);
    batchTask.processTask(batch);
  }
}
