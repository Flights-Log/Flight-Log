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

  //  해당하는 도착 비행기 있는지 확인
  List<flightInfoDTO> searchComeAirplane(String startCity, String arrivalCity, String comeDate);

  //  가는 비행기 좌석 예약
  void reserveGoAirplaneSeats(int goAirplaneFlightId, String goDate, String comeDate,
                              int selectedPeople, String userId, String selectedSeatNames);

  //  오는 비행기 좌석 예약
  void reserveComeAirplaneSeats(int comeAirplaneFlightId, String comeDate, String goDate,
                              int selectedPeople, String userId, String selectedSeatNames);
}
