package com.agoda;

import com.agoda.core.interfaces.BatchManagement;
import com.agoda.entities.Batch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired private BatchManagement batchManagement;

  @RequestMapping("/")
  public ModelAndView welcome() {
    return new ModelAndView("welcome");
  }

  @RequestMapping(value = "/downloadFiles.do", method = RequestMethod.POST)
  public @ResponseBody Map<String, Object> downloadFiles(
      @RequestParam Map<String, Object> params,
      @RequestParam(value = "batchInputFile", required = false) MultipartFile file)
      throws Exception {

    log.info("Received: " + "a request");

    String batchInputText = (String) params.get("batchInputText");
    List<String> downloadLinks = new ArrayList<String>();

    if (!StringUtils.isEmpty(batchInputText)) {
      downloadLinks = getDownloadLinksFromInputText(batchInputText);
    } else {
      downloadLinks = getDownloadLinksFromInputFile(file);
    }
    batchManagement.downloadFiles(downloadLinks);
    return Collections.singletonMap("msg", "Successfully submitted");
  }

  @RequestMapping("/viewSubmittedJobs.do")
  public ModelAndView viewSubmittedJobs(ModelAndView mav) {

    mav.addObject("batch_list", getBatchList());

    return mav;
  }

  private List<Batch> getBatchList() {

    Iterable<Batch> query = batchManagement.query();
    List<Batch> target = new ArrayList<>();
    query.forEach(target::add);
    return target;
    //    List<Batch> batchList = new ArrayList<Batch>();
    //
    //    Batch aBatch = new Batch();
    //    aBatch.setCreatedAt("created at");
    //    aBatch.setId("an id");
    //    aBatch.setUpdatedAt("updated at");
    //    aBatch.setStatus(BatchStatus.COMPLETED);
    //
    //    BatchItem anItem = new BatchItem();
    //    anItem.setId("item ID");
    //    anItem.setResourceLocation("URL");
    //    anItem.setStatus(BatchItemStatus.APPROVED);
    //
    //    List<BatchItem> batchItems = new ArrayList<BatchItem>();
    //    batchItems.add(anItem);
    //
    //    aBatch.setBatchItems(batchItems);
    //
    //    batchList.add(aBatch);
    //
    //    return batchList;
  }

  private List<String> getDownloadLinksFromInputText(String inputText) {
    String[] links = inputText.split(System.lineSeparator());
    return new ArrayList<String>(Arrays.asList(links));
  }

  private List<String> getDownloadLinksFromInputFile(MultipartFile file) throws Exception {
    String content = new String(file.getBytes(), "UTF-8");
    return getDownloadLinksFromInputText(content);
  }

  private List<String> fileList() {
    List<String> data = new ArrayList<>();
    data.add("https://archive.org/download/80MegapixelsCameraSampleImage/CF000891.jpg");
    data.add("https://archive.org/download/80MegapixelsCameraSampleImage/CF000221.jpg");
    data.add("https://c2.staticflickr.com/8/7151/6760135001_14c59a1490_o.jpg");
    data.add("ftp://speedtest.tele2.net/5MB.zip");
    // data.add("ftp://speedtest.tele2.net/1GB.zip");

    return data;
  }
}
