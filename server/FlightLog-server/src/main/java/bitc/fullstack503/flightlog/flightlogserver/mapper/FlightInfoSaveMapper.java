package bitc.fullstack503.flightlog.flightlogserver.mapper;

import bitc.fullstack503.flightlog.flightlogserver.dto.dFlightDTO;
import bitc.fullstack503.flightlog.flightlogserver.dto.flightInfoDTO;
import bitc.fullstack503.flightlog.flightlogserver.dto.iFlightDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FlightInfoSaveMapper {
  // 국내 운항 정보
  void insertDFlightInfo(List<dFlightDTO> flightInfoList);

  // 국제 운항 정보
  void insertIFlightInfo(List<iFlightDTO> iflightDTOList);

  //  출발지 목록
  List<String> searchDeparture();

  //  도착지 목록. 출발지로 설정한 값 제외
  List<String> searchArrive(String selectedDeparture);

  //  출발하는 비행기 목록
  List<flightInfoDTO> searchGoAirplane(String startCity, String arrivalCity, String goDate);
}
