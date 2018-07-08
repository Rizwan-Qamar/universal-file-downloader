package com.agoda.core.api;

import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import com.agoda.entities.Batch;
import com.agoda.entities.BatchItem;
import com.agoda.model.ResourceModel;
import com.agoda.protocols.AbstractFileHandler;
import com.agoda.repositories.BatchRepository;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BatchTaskTest.class)
public class BatchTaskTest {

  @InjectMocks private BatchTask batchTask;

  @Mock private BatchRepository batchRepository;
  @Mock private ThreadPoolTaskExecutor threadPoolTaskExecutor;
  @Mock private Batch batch;

  @Before
  public void init() throws Exception {
    MockitoAnnotations.initMocks(this);
    whenNew(ThreadPoolTaskExecutor.class).withNoArguments().thenReturn(threadPoolTaskExecutor);
    //    whenNew(BatchRepository.class).withNoArguments().thenReturn(batchRepository);
    batch.setId("cf2f006c-40f7-49d5-a3a7-8248b060ce9e");
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
  public void processTaskCompleteFlow()
      throws MalformedURLException, ExecutionException, InterruptedException {

    List<BatchItem> batchItems = new ArrayList<>();
    BatchItem batchItem = new BatchItem();
    batchItem.setId("cl5f706c-70f7-49d5-a3a7-8248b060cu8t");
    batchItem.setResourceLocation(
        "http://archive.org/download/80MegapixelsCameraSampleImage/CF000891.jpg");
    batchItems.add(batchItem);

    ResourceModel resourceModel =
        new ResourceModel(
            "cl5f706c-70f7-49d5-a3a7-8248b060cu8t",
            "http://archive.org/download/80MegapixelsCameraSampleImage/CF000891.jpg",
            "",
            "");
    Future<ResourceModel> resourceModelFuture = mock(AsyncResult.class);
    when(threadPoolTaskExecutor.submit(any(AbstractFileHandler.class)))
        .thenReturn(resourceModelFuture);
    when(resourceModelFuture.get()).thenReturn(resourceModel);
    when(batch.getBatchItems()).thenReturn(batchItems);
    when(batchRepository.findOne(batch.getId())).thenReturn(batch);

    batchTask.processTask(batch);
  }
}
