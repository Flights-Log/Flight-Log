package bitc.fullstack503.flightlog.flightlogserver.controller;


import bitc.fullstack503.flightlog.flightlogserver.dto.FlightReservationCheckDTO;
import bitc.fullstack503.flightlog.flightlogserver.service.FlightChooseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/flightLog")
@RestController
public class ReservationCheckController {

    private final FlightChooseService flightChooseService;

    @Autowired
    public ReservationCheckController(FlightChooseService flightChooseService) {
        this.flightChooseService = flightChooseService;
    }

    @GetMapping("/getReservationCheck")
    public List<FlightReservationCheckDTO> getReservationCheck(@RequestParam ("reservationNumber")String reservationNumber) {
        // 서비스에서 반환된 결과를 그대로 반환
        System.out.println("reservationNumber" +reservationNumber);
        return flightChooseService.flightReservationCheck(reservationNumber);
    }
}