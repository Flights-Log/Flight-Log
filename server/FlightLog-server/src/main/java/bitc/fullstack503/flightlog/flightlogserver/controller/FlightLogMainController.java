package bitc.fullstack503.flightlog.flightlogserver.controller;

import bitc.fullstack503.flightlog.flightlogserver.dto.flightInfoDTO;
import bitc.fullstack503.flightlog.flightlogserver.service.FlightLogMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

//flightmain 패키지 안에 있는 거
@Controller
@RequestMapping("/flightLog/main")
@RestController
public class FlightLogMainController {

  @Autowired
  private FlightLogMainService flightlogMainService;

  //  국내 항공 출발지 정보
  @GetMapping("/searchDeparture")
  public List<String> searchDeparture() {
    System.out.println("국내 항공 출발 도시명");
    List<String> searchDepartureList = flightlogMainService.searchDeparture();

    if (searchDepartureList == null || searchDepartureList.isEmpty()) {
      System.out.println("출발 도시 데이터가 없습니다.");
      return Collections.emptyList(); // 빈 리스트 반환
    }

    System.out.println("출발 도시 : " + searchDepartureList);
    System.out.println();
    return searchDepartureList;
  }

  //  항공 도착지 정보
  @GetMapping("/searchArrive/{selectedDeparture}")
  public List<String> searchArrive(@PathVariable("selectedDeparture") String selectedDeparture) {
    System.out.println("출발지 : " + selectedDeparture);

    List<String> searchArriveList
            = flightlogMainService.searchArrive(selectedDeparture);

    if (searchArriveList == null || searchArriveList.isEmpty()) {
      System.out.println("도착 도시 데이터가 없습니다.");
      return Collections.emptyList(); // 빈 리스트 반환
    }

    System.out.println("도착 도시 : " + searchArriveList);
    return searchArriveList;
  }

  //  내가 선택한 출발지, 도착지, 출발일을 바탕으로 그날에 출발하는 비행기가 있는지 확인하기
//  단, db 상의 데이터는 2024 년의 데이터가 많기 때문에 현재 날짜 - 1년 을 실행함
  @GetMapping("searchGoAirplane/{startCity}/{arrivalCity}/{goDate}")
  public List<flightInfoDTO> searchGoAirplane(@PathVariable("startCity") String startCity,
                                              @PathVariable("arrivalCity") String arrivalCity,
                                              @PathVariable("goDate") String goDate) {
    System.out.println();
    System.out.println("출발 비행기 정보 가져오기");
    List<flightInfoDTO> searchGoAirplaneList
            = flightlogMainService.searchGoAirplane(startCity, arrivalCity, goDate);

    if (searchGoAirplaneList == null || searchGoAirplaneList.isEmpty()) {
      System.out.println("해당하는 날짜에 출발하는 비행기가 없습니다");
      return Collections.emptyList(); // 빈 리스트 반환
    }

    for (flightInfoDTO flightInfoDTO : searchGoAirplaneList) {
      System.out.print("항공편 : " + flightInfoDTO.getFlightInfoAirline() + " / ");
      System.out.print("출발 시간 : " + flightInfoDTO.getFlightInfoStartTime() + " / ");
      System.out.println("도착 시간 : " + flightInfoDTO.getFlightInfoArrivalTime());
    }
    return searchGoAirplaneList;
  }
}
