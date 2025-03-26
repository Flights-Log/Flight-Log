package bitc.fullstack503.flightlog.flightlogserver.mapper;

import bitc.fullstack503.flightlog.flightlogserver.dto.FlightReservationCheckDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface FlightChooseServiceMapper {

    // reservationNumber에 해당하는 예약 정보를 List로 반환
    List<FlightReservationCheckDTO> flightReservationCheck(String etReservationNumber);
}
