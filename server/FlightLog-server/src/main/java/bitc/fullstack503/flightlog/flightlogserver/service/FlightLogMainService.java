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
  List<flightInfoDTO> searchComeAirplane(String arrivalCity, String startCity, String comeDate);

  //  가는 비행기 좌석 예약
  void reserveGoAirplaneSeats(String userId, int selectedPeople, int goAirplaneFlightId,
                              String goDate, String selectedSeatNames);

  //  오는 비행기 좌석 예약
  void reserveComeAirplaneSeats(String userId, int selectedPeople,
                                int goAirplaneFlightId, String goDate, String selectedStartSeatNames,
                                int comeAirplaneFlightId, String comeDate, String selectedArriveSeatNames);

  //  가는 비행기 예매된 좌석 배열로 가져오기
  List<String> searchGoAirplaneIsSeatReservated(int goAirplaneFlightId);

  //  오는 비행기 예매된 좌석 배열로 가져오기
  List<String> searchComeAirplaneIsSeatReserved(int comeAirplaneFlightId);
}
