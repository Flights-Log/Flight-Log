package bitc.fullstack503.flightlog.flightlogserver.service;

import bitc.fullstack503.flightlog.flightlogserver.dto.FlightReservationCheckDTO;

import java.util.List;

public interface FlightChooseService {

  // 예약 번호에 해당하는 정보를 반환하는 메서드 정의
  List<FlightReservationCheckDTO> flightReservationCheck(String flightUserId);

  List<FlightReservationCheckDTO> OneWayReservationCheck(String flightUserId);
}