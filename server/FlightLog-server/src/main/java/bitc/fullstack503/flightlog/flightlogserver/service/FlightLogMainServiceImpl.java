package bitc.fullstack503.flightlog.flightlogserver.service;

import bitc.fullstack503.flightlog.flightlogserver.dto.flightInfoDTO;
import bitc.fullstack503.flightlog.flightlogserver.dto.flightUserDTO;
import bitc.fullstack503.flightlog.flightlogserver.mapper.FlightLogMainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightLogMainServiceImpl implements FlightLogMainService {

  @Autowired
  private FlightLogMainMapper flightLogMainMapper;

  //  공항 출발지 목록
  @Override
  public List<String> searchDeparture() {
    return flightLogMainMapper.searchDeparture();
  }

  //  공항 도착지 목록
//  내가 선택한 출발지 제외
  @Override
  public List<String> searchArrive(String selectedDeparture) {
    return flightLogMainMapper.searchArrive(selectedDeparture);
  }

  //  출발 비행기 찾기
  @Override
  public List<flightInfoDTO> searchGoAirplane(String startCity, String arrivalCity, String goDate) {
    return flightLogMainMapper.searchGoAirplane(startCity, arrivalCity, goDate);
  }

  //  도착 비행기 찾기
  @Override
  public List<flightInfoDTO> searchComeAirplane(String arrivalCity, String startCity, String comeDate) {
    return flightLogMainMapper.searchComeAirplane(arrivalCity, startCity, comeDate);
  }

  //  가는 비행기(편도만) 예매할 경우 예매 정보 저장하기
  @Override
  public void reserveGoAirplaneSeats(String userId, int selectedPeople, int goAirplaneFlightId,
                                     String goDate, String selectedSeatNames) {
    flightLogMainMapper.reserveGoAirplaneSeats(userId, selectedPeople, goAirplaneFlightId, goDate, selectedSeatNames);
  }

  //  가는 비행기 + 오는 비행기(왕복) 예매할 경우 예매 정보 저장하기
  @Override
  public void reserveComeAirplaneSeats(String userId, int selectedPeople,
                                       int goAirplaneFlightId, String goDate, String selectedStartSeatNames,
                                       int comeAirplaneFlightId, String comeDate, String selectedArriveSeatNames) {
    flightLogMainMapper.reserveComeAirplaneSeats(userId, selectedPeople,
            goAirplaneFlightId, goDate, selectedStartSeatNames,
            comeAirplaneFlightId, comeDate, selectedArriveSeatNames);
  }

  //  가는 비행기 좌석이 예약되었는지 확인하기
  @Override
  public List<String> searchGoAirplaneIsSeatReservated(int goAirplaneFlightId) {
    return flightLogMainMapper.searchGoAirplaneIsSeatReservated(goAirplaneFlightId);
  }

  //  오는 비행기 좌석이 예약되었는지 확인하기
  @Override
  public List<String> searchComeAirplaneIsSeatReserved(int comeAirplaneFlightId) {
    return flightLogMainMapper.searchComeAirplaneIsSeatReserved(comeAirplaneFlightId);
  }

  //  유저 이름 가져오기
  @Override
  public List<flightUserDTO> searchUserName(String userId) {
    return flightLogMainMapper.searchUserName(userId);
  }

  @Override
  public void goFlightAlone(String passport, String userId, String firstName, String lastName, String selectedSeatName, String luggage) {
    flightLogMainMapper.goFlightAlone(passport, userId, firstName, lastName, selectedSeatName, luggage);
  }
}
