package bitc.fullstack503.flightlog.flightlogserver.service;

import bitc.fullstack503.flightlog.flightlogserver.dto.FlightReservationCheckDTO;
import bitc.fullstack503.flightlog.flightlogserver.dto.flightUserDTO;

import java.util.List;

public interface FlightSideBarService {

    List<FlightReservationCheckDTO> unusercheck(String nonmemberReservationNumber, String nonmemberPassnum);

    // 사용자가 존재하는지 판별
    boolean isUserInfo(String inputUserId, String inputUserPw);

    // 로그인 - 사용자 정보 가져오기
    flightUserDTO getUserInfo(String inputUserId);

    //회원가입
    void joinMember(flightUserDTO flightuserDTO);

    // 본인 정보 확인
    List<flightUserDTO> selectUserInfoById(String userId);

    // 본인 정보 수정
    void updateUserInfo(flightUserDTO dto);
}
