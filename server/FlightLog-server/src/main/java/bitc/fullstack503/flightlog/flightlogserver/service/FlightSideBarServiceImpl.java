package bitc.fullstack503.flightlog.flightlogserver.service;

import bitc.fullstack503.flightlog.flightlogserver.dto.FlightMemberDTO;
import bitc.fullstack503.flightlog.flightlogserver.mapper.FlightSideBarServiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightSideBarServiceImpl implements FlightSideBarService {

    @Autowired
    private FlightSideBarServiceMapper flightSideBarServiceMapper;

    @Override
    public List<FlightMemberDTO> unusercheck(String nonmemberReservationNumber, String nonmemberPassnum) {

        return flightSideBarServiceMapper.unusercheck(nonmemberReservationNumber,nonmemberPassnum);
    }
}
