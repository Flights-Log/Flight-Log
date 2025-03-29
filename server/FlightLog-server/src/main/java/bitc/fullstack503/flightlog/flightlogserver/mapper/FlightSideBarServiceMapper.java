package bitc.fullstack503.flightlog.flightlogserver.mapper;

import bitc.fullstack503.flightlog.flightlogserver.dto.FlightMemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface FlightSideBarServiceMapper {
    List<FlightMemberDTO> unusercheck(String nonmemberReservationNumber, String nonmemberPassnum);
}
