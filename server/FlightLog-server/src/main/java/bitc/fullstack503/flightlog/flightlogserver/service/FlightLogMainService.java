package bitc.fullstack503.flightlog.flightlogserver.service;

import bitc.fullstack503.flightlog.flightlogserver.dto.flightInfoDTO;
import java.util.List;

public interface FlightLogMainService {
  //  출발지
  List<String> searchDeparture();

  //  도착지
  List<String> searchArrive(String selectedDeparture);

  //  해당하는 출발 비행기 있는지 확인
  List<flightInfoDTO> searchGoAirplane(String startCity, String arrivalCity, String goDate);
}
