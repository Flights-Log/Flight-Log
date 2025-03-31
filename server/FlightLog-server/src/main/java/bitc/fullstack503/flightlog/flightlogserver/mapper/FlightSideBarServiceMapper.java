package bitc.fullstack503.flightlog.flightlogserver.mapper;

import bitc.fullstack503.flightlog.flightlogserver.dto.FlightReservationCheckDTO;
import bitc.fullstack503.flightlog.flightlogserver.dto.flightUserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface FlightSideBarServiceMapper {
    List<FlightReservationCheckDTO> unusercheck(String nonmemberReservationNumber, String nonmemberPassnum);

    // 사용자 존재 판별
    int isUserInfo(String inputUserId, String inputUserPw);

    // 사용자 정보 가져오기
    flightUserDTO getUserInfo(String inputUserId);

    // 회원가입
    void joinMember(flightUserDTO flightuserDTO);

    // 본인 정보 확인
    List<flightUserDTO> selectUserInfoById(String userId);

    // 본인 정보 수정
    void updateUserInfo(flightUserDTO dto);
}
