package com.agoda.core.api;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BatchManagement.class)
public class BatchManagementTest {
  @InjectMocks private BatchManagement batchManagement;

  @Before
  public void init() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void validateUrlFalse() {
    List<String> urls = new ArrayList<>();
    urls.add("http://archive.org/download/80MegapixelsCameraSampleImage/CF000891.jpg");
    urls.add("htt://archive.org/download/80MegapixelsCameraSampleImage/CF000891.jpg");
    Assert.assertFalse(batchManagement.validateUrl(urls));
  }

  @Test
  public void validateUrlTrue() {
    List<String> urls = new ArrayList<>();
    urls.add("http://archive.org/download/80MegapixelsCameraSampleImage/CF000891.jpg");
    urls.add("https://archive.org/download/80MegapixelsCameraSampleImage/CF000891.jpg");
    Assert.assertTrue(batchManagement.validateUrl(urls));
  }
}
