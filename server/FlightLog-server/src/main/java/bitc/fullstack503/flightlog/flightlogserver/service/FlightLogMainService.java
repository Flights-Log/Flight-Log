package bitc.fullstack503.flightlog.flightlogserver.service;

import bitc.fullstack503.flightlog.flightlogserver.dto.flightInfoDTO;
import bitc.fullstack503.flightlog.flightlogserver.dto.flightUserDTO;

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
  void reserveGoAirplaneSeats(String flightReno, String userId, int selectedPeople, int goAirplaneFlightId,
                              String goDate, String selectedSeatNames);

  //  오는 비행기 좌석 예약
  void reserveRoundAirplaneSeats(String flightReno, String userId, int selectedPeople,
                                 int goAirplaneFlightId, String goDate, String selectedStartSeatNames,
                                 int comeAirplaneFlightId, String comeDate, String selectedArriveSeatNames);

  //  가는 비행기 예매된 좌석 배열로 가져오기
  List<String> searchGoAirplaneIsSeatReservated(int goAirplaneFlightId);

  //  오는 비행기 예매된 좌석 배열로 가져오기
  List<String> searchComeAirplaneIsSeatReserved(int comeAirplaneFlightId);

  //  로그인한 사용자의 이름 가져오기
  List<flightUserDTO> searchUserName(String userId);

  //  가는 비행기(편도) 예매하기
  void reserveGoAirplaneMember(String passport, String flightReno, String userId, String firstName, String lastName, String selectedSeatName, int startSeatPrice, String luggage);

  //  혼자서 왕복 비행기 에매하기
  void reserveRoundAirplaneMember(String passport, String roundFlightReno, String userId, String firstName, String lastName, String selectedSeatName, int startSeatPrice, String luggage, String selectedArriveSeatName, int arriveSeatPrice);

  //  다른 날짜 추천
  String recommendStartDate(String startCity, String arrivalCity);

  //  편도 비행기 제거
  void deleteOneWayFlight(String flightReno);

  //  왕복 하고 싶었는데 오는게 없을 때편도 비행기 제거
  void deleteOneWayFlightNoCome(String noComeAirplaneReno);

  //  왕복 비행기 제거
  void deleteRoundFlight(String roundFlightReno);
}
