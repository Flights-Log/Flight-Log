package bitc.fullstack503.flightlog.flightlogserver.mapper;

import bitc.fullstack503.flightlog.flightlogserver.dto.TicketHolderDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Map;

@Mapper
public interface TicketHolderMapper {

  // 항공편 승객 정보 조회
  List<TicketHolderDTO> selectFlightPassengers(String userMemId);

  // 결제 상태 업데이트 메소드
  void updatePaymentStatus(@Param("flightPassport") String flightPassport,
                           @Param("flightReno") String flightReno,
                           @Param("flightIds") List<Integer> flightIds);

  // 티켓 삭제 메소드
  void deleteFlightReserveMember( String flightReno, String flightMemStartSeatNum);
}
