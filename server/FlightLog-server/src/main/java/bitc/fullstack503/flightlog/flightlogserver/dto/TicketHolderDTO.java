package bitc.fullstack503.flightlog.flightlogserver.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TicketHolderDTO {

  @JsonProperty("flightId")
  private Integer flightId;   // 비행편

  @JsonProperty("flightStartId")
  private Integer flightStartId;

  @JsonProperty("flightArrId")
  private Integer flightArrId;

  @JsonProperty("flightPassport")
  private String flightPassport;       // 여권번호

  @JsonProperty("flightUserId")
  private String flightUserId;     // 그룹명

  @JsonProperty("flightMemFirstName")
  private String flightMemFirstName;  // 이름

  @JsonProperty("flightMemLastName")
  private String flightMemLastName;  // 성

  @JsonProperty("flightReno")
  private String flightReno;         // 예약번호

  @JsonProperty("flightReserveSDate")
  private String flightReserveSDate;      // 출발 날짜

  @JsonProperty("flightInfoStartCity")
  private String flightInfoStartCity;      // 출발 도시

  @JsonProperty("flightInfoStartTime")
  private String flightInfoStartTime;      // 출발 시간

  @JsonProperty("flightInfoArrivalCity")
  private String flightInfoArrivalCity;    // 도착 도시

  @JsonProperty("flightInfoArrivalTime")
  private String flightInfoArrivalTime;    // 도착 시간

  @JsonProperty("flightInfoAirline")
  private String flightInfoAirline;      //항공사 이름

  @JsonProperty("flightMemStartSeatNum")
  private String flightMemStartSeatNum;  //출발 좌석 번호

  @JsonProperty("flightMemArriveSeatNum")
  private String flightMemArriveSeatNum; //도착 좌석 번호

  @JsonProperty("selectedSeat")
  private String selectedSeat; // 선택된 좌석

  @JsonProperty("flightMemStartPrice")
  private Integer flightMemStartPrice; // 출발편 가격

  @JsonProperty("flightMemArrPrice")
  private Integer flightMemArrPrice; // 도착편 가격

  @JsonProperty("selectedPrice")
  private Integer selectedPrice; // 선택된 좌석 가격

  @JsonProperty("flightDistance")
  private Double flightDistance;    //비행거리

  @JsonProperty("flightMemLuggage")
  private String flightMemLuggage;     // 수하물 정보

  @JsonProperty("flightArrPayCheck")
  private String flightArrPayCheck;    // 오는편 결제 여부

  @JsonProperty("flightStartPayCheck")
  private String flightStartPayCheck; // 가는편 결제 여부

  @JsonProperty("paymentStatus")
  private String paymentStatus; // 결제 상태 추가 (예: '완료', '진행 중', '취소')
}
