package bitc.fullstack503.flightlog.flightlogserver.mapper;

import bitc.fullstack503.flightlog.flightlogserver.dto.FlightReservationCheckDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface FlightSideBarServiceMapper {
    List<FlightReservationCheckDTO> unusercheck(String nonmemberReservationNumber, String nonmemberPassnum);
}
