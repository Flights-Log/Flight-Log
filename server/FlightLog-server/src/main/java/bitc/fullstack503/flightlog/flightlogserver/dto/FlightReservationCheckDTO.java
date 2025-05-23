package bitc.fullstack503.flightlog.flightlogserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;

@Data
public class FlightReservationCheckDTO {

  @JsonProperty("passport")
  private String passport;  // 여권 번호

  @JsonProperty("flightReno")
  private String flightReno;  // 예약 번호

  @JsonProperty("firstName")
  private String firstName;  // 회원 이름 (이름)

  @JsonProperty("lastName")
  private String lastName;  // 회원 이름 (성)

  @JsonProperty("startCity")
  private String startCity;  // 출발 도시

  @JsonProperty("arrivalCity")
  private String arrivalCity;  // 도착 도시

  @JsonProperty("departureDate")
  private String departureDate;  // 출발 일자

  @JsonProperty("departureTime")
  private String departureTime;  // 출발 시간

  @JsonProperty("arrivalTime")
  private String arrivalTime;  // 도착 시간

  @JsonProperty("numPassengers")
  private int numPassengers;  // 승객 수

  @JsonProperty("seatNumber")
  private String seatNumber;  // 좌석 번호

  @JsonProperty("luggage")
  private String luggage;  // 수하물 정보

  @JsonProperty("returnStartCity")
  private String returnStartCity;  // 돌아오는 출발 도시

  @JsonProperty("returnArrivalCity")
  private String returnArrivalCity;  // 돌아오는 도착 도시

  @JsonProperty("returnDepartureDate")
  private String returnDepartureDate;  // 돌아오는 출발 일자

  @JsonProperty("returnDepartureTime")
  private String returnDepartureTime;  // 돌아오는 출발 시간

  @JsonProperty("returnArrivalTime")
  private String returnArrivalTime;  // 돌아오는 도착 시간

  @JsonProperty("returnSeatNumber")
  private String returnSeatNumber;  // 돌아오는 좌석 번호

  @JsonProperty("flightStartPatCheck")
  private String flightStartPatCheck;

  @JsonProperty("flightArrPayCheck")
  private String flightArrPayCheck;  // 도착지 결제 확인

  @JsonProperty("flightArrId")
  private int flightArrId;  // 도착 ID

}
