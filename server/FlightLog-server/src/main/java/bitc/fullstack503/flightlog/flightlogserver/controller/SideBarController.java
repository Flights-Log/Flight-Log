package bitc.fullstack503.flightlog.flightlogserver.controller;


import bitc.fullstack503.flightlog.flightlogserver.dto.FlightReservationCheckDTO;
import bitc.fullstack503.flightlog.flightlogserver.dto.flightUserDTO;
import bitc.fullstack503.flightlog.flightlogserver.service.FlightSideBarService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/flightLog")
@RestController
public class SideBarController {

    @Autowired
    private FlightSideBarService flightSideBarService;

    @PostMapping("/postUnuserSearch")
    //@body
    public List<FlightReservationCheckDTO>  postMemberInfo(@RequestParam ("nonmemberReservationNumber") String nonmemberReservationNumber,
                                                           @RequestParam ("nonmemberPassnum") String  nonmemberPassnum) {

        List<FlightReservationCheckDTO> flightMemberList;

        System.out.println(nonmemberReservationNumber);
        System.out.println(nonmemberPassnum);

        flightMemberList = flightSideBarService.unusercheck(nonmemberReservationNumber, nonmemberPassnum);

        return flightMemberList;
    }

    //    로그인
    @PostMapping("/flightLogin")
    public Map<String, Object> flightLogin(@RequestParam String inputUserId, @RequestParam String inputUserPw, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("inputUserId: " + inputUserId);
        System.out.println("inputUserPw: " + inputUserPw);

        Map<String, Object> resultMap = new HashMap<>();

        boolean result = flightSideBarService.isUserInfo(inputUserId, inputUserPw);

        if (result) {

            flightUserDTO user = flightSideBarService.getUserInfo(inputUserId);

            HttpSession session = request.getSession();

            // 세션 - 데이터 추가
            session.setAttribute("userId", user.getFlightUserId());
            session.setAttribute("userName", user.getFlightUserKoLastname());
            session.setMaxInactiveInterval(60 * 60 * 1);
            System.out.println("1시간 로그인 유지");

            resultMap.put("success", true);
            resultMap.put("user", user); // 단일 DTO
        } else {
            resultMap.put("success", false);
            resultMap.put("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
        }
        return resultMap;
    }

    @PostMapping("/joinMember")
    public void joinMember(@RequestBody flightUserDTO flightuserDTO) {

        System.out.println("회원가입 정보 도착:");
        System.out.println("ID: " + flightuserDTO.getFlightUserId());
        System.out.println("Email: " + flightuserDTO.getFlightUserEmail());
        System.out.println("Phone: " + flightuserDTO.getFlightUserPhone());

        flightSideBarService.joinMember(flightuserDTO);
    }
}
