package bitc.fullstack503.flightlog.flightlogserver.controller;


import bitc.fullstack503.flightlog.flightlogserver.dto.FlightReservationCheckDTO;
import bitc.fullstack503.flightlog.flightlogserver.service.FlightChooseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequestMapping("/flightLog")
@RestController
public class ReservationCheckController {

  @Autowired
  private FlightChooseService flightChooseService;

  @GetMapping("/getReservationCheck")
  public List<FlightReservationCheckDTO> getReservationCheck(@RequestParam("flightUserId") String flightUserId) {
    List<FlightReservationCheckDTO> flightReservationCheckList;

    System.out.println("전달 받은 userId : " + flightUserId);

    flightReservationCheckList = flightChooseService.flightReservationCheck(flightUserId);

    if (flightReservationCheckList != null && !flightReservationCheckList.isEmpty()) {
      System.out.println("Flight Reservation Details:");
      for (FlightReservationCheckDTO dto : flightReservationCheckList) {
        // DTO의 각 필드 값 출력
        System.out.println("예약번호 : " + dto.getFlightReno());
        System.out.println("성 : " + dto.getLastName());
        System.out.println("이름 : " + dto.getFirstName());
        System.out.println("출발도시: " + dto.getStartCity());
        System.out.println("도착도시: " + dto.getArrivalCity());
        System.out.println("출발날자: " + dto.getDepartureDate());
        System.out.println("출발시간: " + dto.getDepartureTime());
        System.out.println("도착시간: " + dto.getArrivalTime());
        System.out.println("인원수: " + dto.getNumPassengers());
        System.out.println("좌석넘버: " + dto.getSeatNumber());
        System.out.println("여권 번호: " + dto.getPassport());
        System.out.println("수화물: " + dto.getLuggage());
        System.out.println("==============돌아오는 표=================");

        System.out.println("돌아오는 출발도시: " + dto.getReturnStartCity());
        System.out.println("돌아오는 도착도시: " + dto.getReturnArrivalCity());
        System.out.println("돌아오는 출발날자: " + dto.getReturnDepartureDate());
        System.out.println("돌아오는 출발시간: " + dto.getReturnDepartureTime());
        System.out.println("돌아오는 도착시간: " + dto.getReturnArrivalTime());
        System.out.println("돌아오는 인원수: " + dto.getNumPassengers());
        System.out.println("돌아오는 좌석넘버: " + dto.getReturnSeatNumber());
        System.out.println("돌아오는 여권번호: " + dto.getPassport());
        System.out.println("돌아오는 수화물: " + dto.getLuggage());
        System.out.println("========================================");
      }
    } else {
      System.out.println("반환 리스트가 비어있음");
    }


    return flightReservationCheckList;
  }

  //    편도
  @GetMapping("/getOneWayReservationCheck")
  public List<FlightReservationCheckDTO> getOneWayReservationCheck(@RequestParam("flightUserId") String flightUserId) {
    List<FlightReservationCheckDTO> flightOneWayReservationCheckList;

    System.out.println(flightUserId);

    flightOneWayReservationCheckList = flightChooseService.OneWayReservationCheck(flightUserId);

    if (flightOneWayReservationCheckList != null && !flightOneWayReservationCheckList.isEmpty()) {
      System.out.println("Flight Reservation Details:");
      for (FlightReservationCheckDTO dto : flightOneWayReservationCheckList) {
        // DTO의 각 필드 값 출력
        System.out.println("예약번호 : " + dto.getFlightReno());
        System.out.println("성 : " + dto.getLastName());
        System.out.println("이름 : " + dto.getFirstName());
        System.out.println("출발도시: " + dto.getStartCity());
        System.out.println("도착도시: " + dto.getArrivalCity());
        System.out.println("출발날자: " + dto.getDepartureDate());
        System.out.println("출발시간: " + dto.getDepartureTime());
        System.out.println("도착시간: " + dto.getArrivalTime());
        System.out.println("인원수: " + dto.getNumPassengers());
        System.out.println("좌석넘버: " + dto.getReturnSeatNumber());
        System.out.println("Passport: " + dto.getPassport());
        System.out.println("수화물: " + dto.getPassport());
        System.out.println("=======================================");
      }
    } else {
      System.out.println("반환 리스트가 비어있음");
    }


    return flightOneWayReservationCheckList;
  }
}