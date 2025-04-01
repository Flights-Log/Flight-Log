package bitc.fullstack503.flightlog.flightlogserver.service;

import bitc.fullstack503.flightlog.flightlogserver.dto.TicketHolderDTO;
import bitc.fullstack503.flightlog.flightlogserver.mapper.TicketHolderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Service
public class TicketHolderServiceImpl implements TicketHolderService {

  @Autowired
  private TicketHolderMapper ticketHolderMapper;

  @Override
  public List<TicketHolderDTO> getFlightPassengers(String userMemId) {
    return ticketHolderMapper.selectFlightPassengers(userMemId);
  }

  @Override
  public void updatePaymentStatus( String flightPassport, String flightReno , List<Integer> flightIds) {
    // 결제 상태 업데이트 메소드 호출
    ticketHolderMapper.updatePaymentStatus( flightPassport, flightReno ,flightIds);
  }
  @Override
  public void deleteFlightReserveMember(String flightReno,String flightMemStartSeatNum ) {
    ticketHolderMapper.deleteFlightReserveMember(flightReno,flightMemStartSeatNum);
  }

}
