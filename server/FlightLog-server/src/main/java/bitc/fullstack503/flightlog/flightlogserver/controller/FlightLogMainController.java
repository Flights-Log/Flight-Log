package bitc.fullstack503.flightlog.flightlogserver.controller;

import bitc.fullstack503.flightlog.flightlogserver.dto.flightInfoDTO;
import bitc.fullstack503.flightlog.flightlogserver.service.FlightLogMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
      System.out.print("항공편 id : " + flightInfoDTO.getFlightId() + " / ");
      System.out.print("항공편 : " + flightInfoDTO.getFlightInfoAirline() + " / ");
      System.out.print("출발 도시 : " + flightInfoDTO.getFlightInfoStartCity() + " / ");
      System.out.print("도착 도시 : " + flightInfoDTO.getFlightInfoArrivalCity() + " / ");
      System.out.print("출발 시간 : " + flightInfoDTO.getFlightInfoStartTime() + " / ");
      System.out.print("도착 시간 : " + flightInfoDTO.getFlightInfoArrivalTime() + " / ");
      System.out.println("거리 : " + flightInfoDTO.getFlightDistance());
    }
    return searchGoAirplaneList;
  }

  //  내가 선택한 출발지, 도착지, 출발일을 바탕으로 그날에 도착하는 비행기가 있는지 확인하기
//  단, db 상의 데이터는 2024 년의 데이터가 많기 때문에 현재 날짜 - 1년 을 실행함
  @GetMapping("searchComeAirplane/{startCity}/{arrivalCity}/{comeDate}")
  public List<flightInfoDTO> searchComeAirplane(@PathVariable("startCity") String arrivalCity,
                                                @PathVariable("arrivalCity") String startCity,
                                                @PathVariable("comeDate") String goDate) {
    System.out.println();
    System.out.println("도착 비행기 정보 가져오기");
    List<flightInfoDTO> searchComeAirplaneList
            = flightlogMainService.searchComeAirplane(arrivalCity, startCity, goDate);

    if (searchComeAirplaneList == null || searchComeAirplaneList.isEmpty()) {
      System.out.println("해당하는 날짜에 도착하는 비행기가 없습니다");
      return Collections.emptyList(); // 빈 리스트 반환
    }

    for (flightInfoDTO flightInfoDTO : searchComeAirplaneList) {
      System.out.print("항공편 id : " + flightInfoDTO.getFlightId() + " / ");
      System.out.print("항공편 : " + flightInfoDTO.getFlightInfoAirline() + " / ");
      System.out.print("출발 도시 : " + flightInfoDTO.getFlightInfoStartCity() + " / ");
      System.out.print("도착 도시 : " + flightInfoDTO.getFlightInfoArrivalCity() + " / ");
      System.out.print("출발 시간 : " + flightInfoDTO.getFlightInfoStartTime() + " / ");
      System.out.print("도착 시간 : " + flightInfoDTO.getFlightInfoArrivalTime() + " / ");
      System.out.println("거리 : " + flightInfoDTO.getFlightDistance());
    }
    return searchComeAirplaneList;
  }

  //  가는 비행기 예약
  @PutMapping("reserveGoSeat/{userId}/{selectedPeople}/{goAirplaneFlightId}/{goDate}/{selectedSeatNames}")
  public void reserveGoAirplaneSeats(@PathVariable("userId") String userId,
                                     @PathVariable("selectedPeople") int selectedPeople,
                                     @PathVariable("goAirplaneFlightId") int goAirplaneFlightId,
                                     @PathVariable("goDate") String goDate,
                                     @PathVariable("selectedSeatNames") String selectedSeatNames) {
    flightlogMainService.reserveGoAirplaneSeats(userId, selectedPeople, goAirplaneFlightId, goDate, selectedSeatNames);
  }

  //  오는 비행기 예약
  @PutMapping("reserveComeSeat/{userId}/{selectedPeople}/" +
          "{goAirplaneFlightId}/{goDate}/{selectedStartSeatNames}/" +
          "{comeAirplaneFlightId}/{comeDate}/{selectedArriveSeatNames}")
  public void reserveComeAirplaneSeats(@PathVariable("userId") String userId,
                                       @PathVariable("selectedPeople") int selectedPeople,
                                       @PathVariable("goAirplaneFlightId") int goAirplaneFlightId,
                                       @PathVariable("goDate") String goDate,
                                       @PathVariable("selectedStartSeatNames") String selectedStartSeatNames,
                                       @PathVariable("comeAirplaneFlightId") int comeAirplaneFlightId,
                                       @PathVariable("comeDate") String comeDate,
                                       @PathVariable("selectedArriveSeatNames") String selectedArriveSeatNames) {
    flightlogMainService.reserveComeAirplaneSeats(userId, selectedPeople,
            goAirplaneFlightId, goDate, selectedStartSeatNames,
            comeAirplaneFlightId, comeDate, selectedArriveSeatNames);
  }

  //  가는 비행기 예매된 좌석 확인하기
  @GetMapping("goAirplaneIsSeatReservated/{goAirplaneFlightId}")
  public List<String> searchGoAirplaneIsSeatReservated(@PathVariable("goAirplaneFlightId") int goAirplaneFlightId) {
    List<String> goReservatedSeatList = flightlogMainService.searchGoAirplaneIsSeatReservated(goAirplaneFlightId);

    if (goReservatedSeatList == null || goReservatedSeatList.isEmpty()) {
      return Collections.emptyList();
    }

    System.out.println("예약된 좌석 : " + goReservatedSeatList);
    System.out.println();

    return goReservatedSeatList;
  }

  //  오는 비행기 예매된 좌석 확인하기
  @GetMapping("comeAirplaneIsSeatReservated/{comeAirplaneFlightId}")
  public List<String> searchComeAirplaneIsSeatReserved(@PathVariable("comeAirplaneFlightId") int comeAirplaneFlightId) {
    List<String> comeReservatedSeatList = flightlogMainService.searchComeAirplaneIsSeatReserved(comeAirplaneFlightId);

    if (comeReservatedSeatList == null || comeReservatedSeatList.isEmpty()) {
      return Collections.emptyList();
    }

    System.out.println("예약된 좌석 : " + comeReservatedSeatList);
    System.out.println();

    return comeReservatedSeatList;
  }
}
