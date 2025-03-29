package bitc.fullstack503.flightlog.flightlogserver.controller;


import bitc.fullstack503.flightlog.flightlogserver.dto.FlightReservationCheckDTO;
import bitc.fullstack503.flightlog.flightlogserver.service.FlightSideBarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/flightLog")
@RestController
public class SideBarController {

    @Autowired
    private FlightSideBarService flightSideBarService;

    @PostMapping("/postUnuserSearch")
    //@body
    public List<FlightReservationCheckDTO>  postMemberInfo(@RequestParam ("nonmemberReservationNumber") String nonmemberReservationNumber,
                                                           @RequestParam ("nonmemberPassnum") String  nonmemberPassnum) {

        List<FlightReservationCheckDTO> flightMemberList;

        System.out.println(nonmemberReservationNumber);
        System.out.println(nonmemberPassnum);

        flightMemberList = flightSideBarService.unusercheck(nonmemberReservationNumber, nonmemberPassnum);

        return flightMemberList;
    }
}
