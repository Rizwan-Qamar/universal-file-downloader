package com.agoda;

import com.agoda.core.interfaces.BatchManagement;
import com.agoda.entities.Batch;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired private BatchManagement batchManagement;

  @Autowired ServletContext context;

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

  @RequestMapping("/approveItem.do")
  public @ResponseBody Map<String, Object> approveItem(@RequestParam Map<String, Object> params)
      throws Exception {

    log.info("Received: " + "a request");
    String itemId = (String) params.get("itemId");
    boolean result = batchManagement.isApproved(itemId);

    Map<String, Object> results = new HashMap<String, Object>();
    if (result) {
      results.put("STATUS", "SUCCESS");
      return results;
    } else {
      results.put("STATUS", "FAILURE");
      return results;
    }
  }

  @RequestMapping("/rejectItem.do")
  public @ResponseBody Map<String, Object> rejectItem(@RequestParam Map<String, Object> params)
      throws Exception {

    log.info("Received: " + "a request");
    String itemId = (String) params.get("itemId");
    boolean result = batchManagement.isRejected(itemId);

    Map<String, Object> results = new HashMap<String, Object>();
    if (result) {
      results.put("STATUS", "SUCCESS");
      return results;
    } else {
      results.put("STATUS", "FAILURE");
      return results;
    }
  }

  @RequestMapping("/download/{fileName:.+}")
  public void downloader(
      HttpServletRequest request,
      HttpServletResponse response,
      @PathVariable("fileName") String fileName) {
    try {
      String downloadFolder = context.getRealPath("/downloads");
      File file = new File(downloadFolder + File.separator + fileName);

      if (file.exists()) {
        String mimeType = context.getMimeType(file.getPath());

        if (mimeType == null) {
          mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setContentLength((int) file.length());

        OutputStream os = response.getOutputStream();
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[4096];
        int b = -1;

        while ((b = fis.read(buffer)) != -1) {
          os.write(buffer, 0, b);
        }

        fis.close();
        os.close();
      } else {
        System.out.println("Requested " + fileName + " file not found!!");
      }
    } catch (IOException e) {
      System.out.println("Error:- " + e.getMessage());
    }
  }
}
