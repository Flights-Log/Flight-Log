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

//    @Autowired
//    public ReservationCheckController(FlightChooseService flightChooseService) {
//        this.flightChooseService = flightChooseService;
//    }

    @GetMapping("/getReservationCheck")
    public List<FlightReservationCheckDTO> getReservationCheck(@RequestParam("reservationNumber") String reservationNumber) {
        List<FlightReservationCheckDTO> flightReservationCheckList;

        System.out.println(reservationNumber);

        flightReservationCheckList = flightChooseService.flightReservationCheck(reservationNumber);

        if (flightReservationCheckList != null && !flightReservationCheckList.isEmpty()) {
            System.out.println("Flight Reservation Details:");
            for (FlightReservationCheckDTO dto : flightReservationCheckList) {
                // DTO의 각 필드 값 출력
                System.out.println("예약번호 : " + dto.getFlightReno());
                System.out.println("성 : " + dto.getFlightMemberLastName());
                System.out.println("이름 : " + dto.getFlightMemberFirstName());
                System.out.println("출발도시: " + dto.getFlightStartCity());
                System.out.println("도착도시: " + dto.getFlightArrivalCity());
                System.out.println("출발날자: " + dto.getFlightReserveStartDate());
                System.out.println("출발시간: " + dto.getFlightInfoStartTime());
                System.out.println("도착날짜: " + dto.getFlightReserveEndDate());
                System.out.println("도착시간: " + dto.getFlightInfoArrivalTime());
                System.out.println("인원수: " + dto.getFlightMemberNum());
                System.out.println("좌석넘버: " + dto.getFlightMemStartSeatNum());
                System.out.println("Passport: " + dto.getFlightPassport());
                System.out.println("수화물: " + dto.getFlightMemLuggage());
                System.out.println("=======================================");
            }
        } else {
            System.out.println("반환 리스트가 비어있음");
        }


        return flightReservationCheckList;
    }
}