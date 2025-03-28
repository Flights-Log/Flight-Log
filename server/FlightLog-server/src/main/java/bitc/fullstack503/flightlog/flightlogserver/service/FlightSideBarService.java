package bitc.fullstack503.flightlog.flightlogserver.service;

import bitc.fullstack503.flightlog.flightlogserver.dto.FlightMemberDTO;

import java.util.List;

public interface FlightSideBarService {

    List<FlightMemberDTO> unusercheck(String nonmemberReservationNumber, String nonmemberPassnum);
}
