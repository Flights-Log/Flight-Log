package bitc.fullstack503.flightlog.flightlogserver.mapper;

import bitc.fullstack503.flightlog.flightlogserver.dto.flightInfoDTO;
import bitc.fullstack503.flightlog.flightlogserver.dto.flightUserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FlightLogMainMapper {
  //  출발지 목록
  List<String> searchDeparture();

  //  도착지 목록. 출발지로 설정한 값 제외
  List<String> searchArrive(String selectedDeparture);

  //  출발하는 비행기 목록
  List<flightInfoDTO> searchGoAirplane(String startCity, String arrivalCity, String goDate);

  //  도착하는 비행기 목록
  List<flightInfoDTO> searchComeAirplane(String arrivalCity, String startCity, String comeDate);

  //  편도 비행기 좌석 예약하기
  void reserveGoAirplaneSeats(String flightReno, String userId, int selectedPeople, int goAirplaneFlightId,
                              String goDate, String selectedSeatNames);

  //  왕복 비행기 좌석 예약하기
  void reserveRoundAirplaneSeats(String flightReno, String userId, int selectedPeople,
                                 int goAirplaneFlightId, String goDate, String selectedStartSeatNames,
                                 int comeAirplaneFlightId, String comeDate, String selectedArriveSeatNames);

  //  가는 비행기의 예약된 좌석 배열로 가져오기
  List<String> searchGoAirplaneIsSeatReservated(int goAirplaneFlightId);

  //  오는 비행기의 예약된 좌석 배열로 가져오기
  List<String> searchComeAirplaneIsSeatReserved(int comeAirplaneFlightId);

  //  유저 이름 가져오기
  List<flightUserDTO> searchUserName(String userId);

  //  편도 비행기 예매하기
  void reserveGoAirplaneMember(String passport, String flightReno, String userId, String firstName, String lastName,
                               String selectedSeatName, String luggage);

  //  왕복 비행기 혼자 예매하기
  void reserveRoundAirplaneMember(String passport, String roundFlightReno, String userId, String firstName, String lastName,
                                  String selectedStartSeatName, String luggage, String selectedArriveSeatName);
}
