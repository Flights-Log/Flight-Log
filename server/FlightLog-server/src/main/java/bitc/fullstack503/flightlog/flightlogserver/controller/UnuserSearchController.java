package bitc.fullstack503.flightlog.flightlogserver.controller;


import bitc.fullstack503.flightlog.flightlogserver.dto.FlightMemberDTO;
import bitc.fullstack503.flightlog.flightlogserver.service.FlightSideBarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/flightLog")
@RestController
public class UnuserSearchController {

    @Autowired
    private FlightSideBarService flightSideBarService;

    @PostMapping("/postUnuserSearch")
    //@body
    public List<FlightMemberDTO>  postMemberInfo(@RequestParam ("nonmemberReservationNumber") String nonmemberReservationNumber,
                                 @RequestParam ("nonmemberPassnum") String  nonmemberPassnum) {

        List<FlightMemberDTO> flightMemberList;

        System.out.println(nonmemberReservationNumber);
        System.out.println(nonmemberPassnum);

        flightMemberList = flightSideBarService.unusercheck(nonmemberReservationNumber, nonmemberPassnum);

        return flightMemberList;
    }
}
