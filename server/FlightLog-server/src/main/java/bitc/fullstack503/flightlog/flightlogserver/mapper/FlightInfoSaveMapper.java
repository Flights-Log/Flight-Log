package bitc.fullstack503.flightlog.flightlogserver.mapper;

import bitc.fullstack503.flightlog.flightlogserver.dto.dFlightDTO;
import bitc.fullstack503.flightlog.flightlogserver.dto.iFlightDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FlightInfoSaveMapper {
  // 국내 운항 정보
  void insertDFlightInfo(List<dFlightDTO> flightInfoList);

  // 국제 운항 정보
  void insertIFlightInfo(List<iFlightDTO> iflightDTOList);
}
