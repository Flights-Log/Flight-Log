package bitc.fullstack503.flightlog.flightlogserver.service;

import bitc.fullstack503.flightlog.flightlogserver.dto.FlightReservationCheckDTO;
import bitc.fullstack503.flightlog.flightlogserver.mapper.FlightChooseServiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightChooseServiceImpl implements FlightChooseService {

    @Autowired
    private FlightChooseServiceMapper flightChooseServiceMapper;

    @Override
    public List<FlightReservationCheckDTO> flightReservationCheck(String reservationNumber) {

        List<FlightReservationCheckDTO> test = flightChooseServiceMapper.flightReservationCheck(reservationNumber);

        System.out.println(test);

        return flightChooseServiceMapper.flightReservationCheck(reservationNumber);
    }
}