package bitc.fullstack503.flightlog.flightlogserver.service;

import bitc.fullstack503.flightlog.flightlogserver.dto.flightInfoDTO;
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
  public List<flightInfoDTO> searchComeAirplane(String startCity, String arrivalCity, String comeDate) {
    return flightLogMainMapper.searchGoAirplane(startCity, arrivalCity, comeDate);
  }
}
