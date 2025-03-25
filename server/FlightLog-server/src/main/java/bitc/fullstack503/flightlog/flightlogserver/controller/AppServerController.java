package bitc.fullstack503.flightlog.flightlogserver.controller;

//import org.springframework.web.bind.annotation.*;

import bitc.fullstack503.flightlog.flightlogserver.dto.dFlightDTO;
import bitc.fullstack503.flightlog.flightlogserver.dto.iFlightDTO;
import bitc.fullstack503.flightlog.flightlogserver.service.FlightInfoSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//  서버 기본 주소
@Controller
@RequestMapping("/flightLog")
@RestController
public class AppServerController {

  @Autowired
  private FlightInfoSaveService flightInfoSaveService;

  @GetMapping("/gettest1")
  public String getTest1() {
    System.out.println("*** retrofit으로 gettest1에 접속 ***");

    return "get test1";
  }

  // 국내 항송 정보 -  DTO 타입을 파라미터로 받을 경우
  @PostMapping("/postDFlightInfo")
  public List<dFlightDTO> postDFlightInfo(@RequestBody List<dFlightDTO> flightInfoList) {
    System.out.println("*** 앱으로부터 국내 운항일정 전송 받음 ***");

    // flightInfoList의 모든 항공편 정보를 출력
    for (dFlightDTO flightInfo : flightInfoList) {
      System.out.println("항공사 : " + flightInfo.getAirlineKorean());
      System.out.println("항공편명 : " + flightInfo.getDomesticNum());
      System.out.println("운항 시작 일정 : " + flightInfo.getDomesticStdate());
      System.out.println("운항 종료 일정 : " + flightInfo.getDomesticEddate());
      System.out.println("출발지역 : " + flightInfo.getStartcity());
      System.out.println("출발시간 : " + flightInfo.getDomesticStartTime());
      System.out.println("도착지역 : " + flightInfo.getArrivalcity());
      System.out.println("도착시간 : " + flightInfo.getDomesticArrivalTime());
      System.out.println("월요일 일정 : " + flightInfo.getDomesticMon());
      System.out.println("화요일 일정 : " + flightInfo.getDomesticTue());
      System.out.println("수요일 일정 : " + flightInfo.getDomesticWed());
      System.out.println("목요일 일정 : " + flightInfo.getDomesticThu());
      System.out.println("금요일 일정 : " + flightInfo.getDomesticFri());
      System.out.println("토요일 일정 : " + flightInfo.getDomesticSat());
      System.out.println("일요일 일정 : " + flightInfo.getDomesticSun());
    }

    flightInfoSaveService.saveDFlightInfo(flightInfoList);

    return flightInfoList;
  }

  // 국내 항송 정보 -  DTO 타입을 파라미터로 받을 경우
  @PostMapping("/postIFlightInfo")
  public List<iFlightDTO> postIFlightInfo(@RequestBody List<iFlightDTO> IflightDTOList) {
    System.out.println("*** 앱으로부터 국제 운항일정 전송 받음 ***");

    // flightInfoList의 모든 항공편 정보를 출력
    for (iFlightDTO flightInfo : IflightDTOList) {
      System.out.println("항공사 : " + flightInfo.getAirlineKorean());
      System.out.println("항공편명 : " + flightInfo.getInternationalNum());
      System.out.println("운항 시작 일정 : " + flightInfo.getInternationalStdate());
      System.out.println("운항 종료 일정 : " + flightInfo.getInternationalEddate());
      System.out.println("출발지역 : " + flightInfo.getDeptcity());
      System.out.println("출발시간 : " + flightInfo.getInternationalTime());
      System.out.println("도착지역 : " + flightInfo.getArrvcity());
      System.out.println("월요일 일정 : " + flightInfo.getInternationalMon());
      System.out.println("화요일 일정 : " + flightInfo.getInternationalTue());
      System.out.println("수요일 일정 : " + flightInfo.getInternationalWed());
      System.out.println("목요일 일정 : " + flightInfo.getInternationalThu());
      System.out.println("금요일 일정 : " + flightInfo.getInternationalFri());
      System.out.println("토요일 일정 : " + flightInfo.getInternationalSat());
      System.out.println("일요일 일정 : " + flightInfo.getInternationalSun());
    }

    flightInfoSaveService.saveIFlightInfo(IflightDTOList);

    return IflightDTOList;
  }


  //  국내 항공 출발지 정보
  @GetMapping("/searchDeparture")
  public List<String> searchDeparture() {
    System.out.println("국내 항공 출발 도시명");
    List<String> searchDepartureList = flightInfoSaveService.searchDeparture();

    if (searchDepartureList == null || searchDepartureList.isEmpty()) {
      System.out.println("출발 도시 데이터가 없습니다.");
      return Collections.emptyList(); // 빈 리스트 반환
    }

    System.out.println("출발 도시 : " + searchDepartureList);
    return searchDepartureList;
  }

  //  항공 도착지 정보
  @GetMapping("/searchArrive/{selectedDeparture}")
  public List<String> searchArrive(@PathVariable("selectedDeparture") String selectedDeparture) {
    System.out.println("도착지");
    List<String> searchArriveList
            = flightInfoSaveService.searchArrive(selectedDeparture);

    if (searchArriveList == null || searchArriveList.isEmpty()) {
      System.out.println("도착 도시 데이터가 없습니다.");
      return Collections.emptyList(); // 빈 리스트 반환
    }

    System.out.println("도착 도시 : " + searchArriveList);
    return searchArriveList;
  }
}












