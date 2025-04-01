package bitc.fullstack503.flightlog.flightlogserver.service;

import bitc.fullstack503.flightlog.flightlogserver.dto.TicketHolderDTO;

import java.util.List;
import java.util.Map;

public interface TicketHolderService {

  List<TicketHolderDTO> getFlightPassengers(String userMemId);

  // 결제 상태 업데이트
  void updatePaymentStatus( String flightPassport, String flightReno, List<Integer> flightIds);

  // 티켓 삭제 메소드
  void deleteFlightReserveMember(String  flightReno, String flightMemStartSeatNum);
}
