package bitc.fullstack503.flightlog.flightlogserver.controller;

//import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//  서버 기본 주소
@Controller
@RequestMapping("/flightLog")
@RestController
public class AppServerController {
  
  @GetMapping("/gettest1")
  public String getTest1() {
    System.out.println("*** retrofit으로 gettest1에 접속 ***");

    return "get test1";
  }

}












