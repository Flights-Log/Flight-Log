package bitc.fullstack503.flightlog.flightlogserver.service;

import bitc.fullstack503.flightlog.flightlogserver.dto.FlightReservationCheckDTO;

import java.util.List;

public interface FlightSideBarService {

    List<FlightReservationCheckDTO> unusercheck(String nonmemberReservationNumber, String nonmemberPassnum);
}
