package bitc.fullstack503.flightlog.flightlogserver.service;

import bitc.fullstack503.flightlog.flightlogserver.dto.dFlightDTO;
import bitc.fullstack503.flightlog.flightlogserver.dto.iFlightDTO;
import bitc.fullstack503.flightlog.flightlogserver.mapper.FlightInfoSaveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightInfoSaveServiceImpl implements FlightInfoSaveService {

  @Autowired
  private FlightInfoSaveMapper flightInfoSaveMapper;

  // 국내 운항 정보
  @Override
  public void saveDFlightInfo(List<dFlightDTO> flightInfoList) {
    flightInfoSaveMapper.insertDFlightInfo(flightInfoList);
  }

  // 국제 운항 정보
  @Override
  public void saveIFlightInfo(List<iFlightDTO> IflightDTOList) {
    flightInfoSaveMapper.insertIFlightInfo(IflightDTOList);
  }

//  공항 출발지 목록
  @Override
  public List<String> searchDeparture() {
    return flightInfoSaveMapper.searchDeparture();
  }

//  공항 도착지 목록
//  내가 선택한 출발지 제외
  @Override
  public List<String> searchArrive(String selectedDeparture) {
    return flightInfoSaveMapper.searchArrive(selectedDeparture);
  }
}
