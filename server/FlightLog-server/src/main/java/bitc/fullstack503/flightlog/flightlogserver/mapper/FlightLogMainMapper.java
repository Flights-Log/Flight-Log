package bitc.fullstack503.flightlog.flightlogserver.mapper;

import bitc.fullstack503.flightlog.flightlogserver.dto.flightInfoDTO;
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
  List<flightInfoDTO> searchComeAirplane(String startCity, String arrivalCity, String comeDate);

  //  가는 비행기 좌석 예약하기
  void reserveGoAirplaneSeats(int goAirplaneFlightId, String goDate, String comeDate,
                              int selectedPeople, String userId, String selectedSeatNames);

  //  오는 비행기 좌석 예약하기
  void reserveComeAirplaneSeats(int comeAirplaneFlightId, String comeDate, String goDate,
                              int selectedPeople, String userId, String selectedSeatNames);
}
