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
    data.add(
        "https://upload.wikimedia.org/wikipedia/commons/thumb/1/12/K2_2006b.jpg/800px-K2_2006b.jpg");
    data.add(
        "https://www.worldatlas.com/r/w728-h425-c728x425/upload/f1/ec/77/prime-minister-s-secretariat-islamabad-by-usman-ghani.jpg");
    return data;
  }
}
