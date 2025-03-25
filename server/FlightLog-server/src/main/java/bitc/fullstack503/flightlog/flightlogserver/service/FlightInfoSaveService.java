package bitc.fullstack503.flightlog.flightlogserver.service;

import bitc.fullstack503.flightlog.flightlogserver.dto.dFlightDTO;
import bitc.fullstack503.flightlog.flightlogserver.dto.iFlightDTO;

import java.util.List;

public interface FlightInfoSaveService {

  // 국내 운항 정보
  void saveDFlightInfo(List<dFlightDTO> flightInfoList);

  // 국제 운항 정보
  void saveIFlightInfo(List<iFlightDTO> IflightDTOList);

//  출발지
  List<String> searchDeparture();

//  도착지
  List<String> searchArrive(String selectedDeparture);
}
