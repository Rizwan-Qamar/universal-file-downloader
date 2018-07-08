package com.agoda;

import com.agoda.core.interfaces.BatchItemManagement;
import com.agoda.core.interfaces.BatchManagement;
import com.agoda.entities.Batch;
import com.agoda.entities.BatchItem;
import java.io.*;
import java.util.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
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
  @Autowired private BatchItemManagement batchItemManagement;

  @Autowired ServletContext context;

  @RequestMapping("/")
  public ModelAndView welcome() {

    log.info("Welcome Request received");
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
  }

  private List<String> getDownloadLinksFromInputText(String inputText) {
    String[] links = inputText.split(System.lineSeparator());
    return new ArrayList<String>(Arrays.asList(links));
  }

  private List<String> getDownloadLinksFromInputFile(MultipartFile file) throws Exception {
    String content = new String(file.getBytes(), "UTF-8");
    return getDownloadLinksFromInputText(content);
  }

  @RequestMapping("/approveItem.do")
  public @ResponseBody Map<String, Object> approveItem(@RequestParam Map<String, Object> params)
      throws Exception {

    log.info("Received: " + "a request");
    String itemId = (String) params.get("itemId");
    boolean result = batchItemManagement.markApproved(itemId);

    Map<String, Object> results = new HashMap<String, Object>();
    return getStringObjectMap(result, results);
  }

  @RequestMapping("/rejectItem.do")
  public @ResponseBody Map<String, Object> rejectItem(@RequestParam Map<String, Object> params)
      throws Exception {

    log.info("Received: " + "a request");
    String itemId = (String) params.get("itemId");
    boolean result = batchItemManagement.markRejected(itemId);

    Map<String, Object> results = new HashMap<String, Object>();
    return getStringObjectMap(result, results);
  }

  private Map<String, Object> getStringObjectMap(boolean result, Map<String, Object> results) {
    if (result) {
      results.put("STATUS", "SUCCESS");
      return results;
    } else {
      results.put("STATUS", "FAILURE");
      return results;
    }
  }

  @RequestMapping("/downloadItem/{itemId}")
  public void downloader(
      HttpServletRequest request,
      HttpServletResponse response,
      @PathVariable("itemId") String itemId) {
    try {
      BatchItem batchItem = batchItemManagement.query(itemId);
      String location = batchItem.getLocationOnDisk();
      File file = new File(location);
      if (file.exists()) {
        String mimeType = context.getMimeType(file.getPath());

        if (mimeType == null) {
          mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);
        response.addHeader(
            "Content-Disposition",
            "attachment; filename=" + FilenameUtils.getName(batchItem.getResourceLocation()));
        response.setContentLength((int) file.length());

        try (OutputStream os = new BufferedOutputStream(response.getOutputStream());
            InputStream fis = new BufferedInputStream(new FileInputStream(file))) {

          byte[] buffer = new byte[4096];
          int b = 0;

          while ((b = fis.read(buffer)) >= 0) {
            os.write(buffer, 0, b);
          }
        }

      } else {
        log.error("Requested file not found. ID: " + itemId);
      }
    } catch (IOException e) {
      log.error("Error:- " + e.getMessage());
    }
  }
}
