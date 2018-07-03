package com.agoda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

  private Logger log = LoggerFactory.getLogger(this.getClass());

  //    @Autowired
  ////    private NetworkServiceRecordManagement networkServiceRecordManagement;

  @RequestMapping("/")
  public ModelAndView firstPage() {

    log.info("Received: " + "a request");
    return new ModelAndView("welcome");
  }
}
