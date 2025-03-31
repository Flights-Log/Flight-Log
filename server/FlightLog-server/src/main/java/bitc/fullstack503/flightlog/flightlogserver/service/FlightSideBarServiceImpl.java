package bitc.fullstack503.flightlog.flightlogserver.service;

import bitc.fullstack503.flightlog.flightlogserver.dto.FlightReservationCheckDTO;
import bitc.fullstack503.flightlog.flightlogserver.dto.flightUserDTO;
import bitc.fullstack503.flightlog.flightlogserver.mapper.FlightSideBarServiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightSideBarServiceImpl implements FlightSideBarService {

    @Autowired
    private FlightSideBarServiceMapper flightSideBarServiceMapper;

    @Override
    public List<FlightReservationCheckDTO> unusercheck(String nonmemberReservationNumber, String nonmemberPassnum) {

        return flightSideBarServiceMapper.unusercheck(nonmemberReservationNumber,nonmemberPassnum);
    }

    // 사용자 존재 판별
    @Override
    public boolean isUserInfo(String inputUserId, String inputUserPw) {

        int result = flightSideBarServiceMapper.isUserInfo(inputUserId, inputUserPw);

        if (result == 1) {
            return true;
        } else{
            return false;
        }
    }

    // 사용자 정보 가져오기
    @Override
    public flightUserDTO getUserInfo(String inputUserId) {
        return flightSideBarServiceMapper.getUserInfo(inputUserId);
    }

    // 회원가입
    @Override
    public void joinMember(flightUserDTO flightuserDTO) {
        flightSideBarServiceMapper.joinMember(flightuserDTO);
    }
}
