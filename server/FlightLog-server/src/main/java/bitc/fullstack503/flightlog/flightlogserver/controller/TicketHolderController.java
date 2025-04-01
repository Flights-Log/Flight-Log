package bitc.fullstack503.flightlog.flightlogserver.controller;

import bitc.fullstack503.flightlog.flightlogserver.dto.TicketHolderDTO;
import bitc.fullstack503.flightlog.flightlogserver.service.TicketHolderService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/flightLog")
public class TicketHolderController {

  @Autowired
  private TicketHolderService ticketHolderService;

  // 항공편 승객 정보 조회 --- 3개 테이블을 조인하여 항공편 승객 정보를 반환
  @GetMapping("/getFlightPassengers")
  public List<TicketHolderDTO> getFlightPassengers(@RequestParam("userMemId") String userMemId) {
    System.out.println("*** 항공편 승객 정보 조회 ***");

    // 서비스에서 승객 정보를 조회
    List<TicketHolderDTO> ticketHolderList = ticketHolderService.getFlightPassengers(userMemId);

    // 승객 정보를 출력 (디버그 용)
    ticketHolderList.forEach(ticketHolder -> {
      System.out.println("여권번호 : " + ticketHolder.getFlightPassport());
      System.out.println("티켓팅한 ID : " + ticketHolder.getFlightUserId());
      System.out.println(" 성 : " + ticketHolder.getFlightMemLastName());
      System.out.println(" 이름 : " + ticketHolder.getFlightMemFirstName());
      System.out.println("출발일자 : " + ticketHolder.getFlightReserveSDate());
      System.out.println("항공사 : " + ticketHolder.getFlightInfoAirline());
      System.out.println("출발지역 : " + ticketHolder.getFlightInfoStartCity());
      System.out.println("출발시간 : " + ticketHolder.getFlightInfoStartTime());
      System.out.println("도착지역 : " + ticketHolder.getFlightInfoArrivalCity());
      System.out.println("도착시간 : " + ticketHolder.getFlightInfoArrivalTime());
      System.out.println("출발 좌석번호 : " + ticketHolder.getFlightMemStartSeatNum());
      System.out.println("도착 좌석번호 : " + ticketHolder.getFlightMemArriveSeatNum());
      System.out.println("선택된 좌석 : + " + ticketHolder.getSelectedSeat());
      System.out.println("출발편 가격 : " + ticketHolder.getFlightMemStartPrice());
      System.out.println("도착편 가격 : " + ticketHolder.getFlightMemArrPrice());
      System.out.println("선택된 가격 : " + ticketHolder.getSelectedPrice());
      System.out.println("비행거리 : "+ticketHolder.getFlightDistance());
      System.out.println("수하물 정보 : "+ticketHolder.getFlightMemLuggage());
      System.out.println("오는날 결제여부 : " + ticketHolder.getFlightArrPayCheck());
      System.out.println("가는날 결제여부 : " + ticketHolder.getFlightStartPayCheck());
    });

    // 승객 정보 반환
    return ticketHolderList;
  }



  @ResponseBody
  @PostMapping("/updatePaymentStatus")
  public String updatePaymentStatus(@RequestParam("flightPassport") String flightPassport,
                                    @RequestParam("flightReno") String flightReno,
                                    @RequestParam(value =  "flightIds", required = false) List<Integer> flightIds) {
    System.out.println("*** 결제 상태 업데이트 ***");
    System.out.println("flightPassport: " + flightPassport);
    System.out.println("flightReno: " + flightReno);
    System.out.println("flightIds: " + (flightIds != null ? flightIds : "NULL")); // Log로 확인

    if (flightIds == null || flightIds.isEmpty()) {
      System.out.println("⚠️ flightIds is null or empty");
      flightIds = Collections.emptyList(); // null이면 빈 리스트로 처리
    }

    // 서비스 호출
    ticketHolderService.updatePaymentStatus(flightPassport, flightReno, flightIds);

    // 결제 상태 업데이트 성공 여부 반환
    return "success";
  }

  @PostMapping("/deleteTicket")
  public String deleteFlightReserveMember(@RequestParam("flightReno") String flightReno, @RequestParam("flightMemStartSeatNum") String flightMemStartSeatNum ) {
    System.out.println("*** 티켓 삭제 ***");

    // 서비스 호출
  ticketHolderService.deleteFlightReserveMember(flightReno,flightMemStartSeatNum);

return "success";
  }
}
