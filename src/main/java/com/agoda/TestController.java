package com.agoda;

import com.agoda.core.interfaces.FileHandlerManagement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired private FileHandlerManagement fileHandlerManagement;

  @RequestMapping("/")
  public ModelAndView firstPage() {

    log.info("Received: " + "a request");
    fileHandlerManagement.downloadFiles(fileList());
    return new ModelAndView("welcome");
  }

  private List<String> fileList() {
    List<String> data = new ArrayList<>();
    data.add("https://archive.org/download/80MegapixelsCameraSampleImage/CF000891.jpg");
    data.add("https://archive.org/download/80MegapixelsCameraSampleImage/CF000221.jpg");
    data.add("https://c2.staticflickr.com/8/7151/6760135001_14c59a1490_o.jpg");
    return data;
  }
}
