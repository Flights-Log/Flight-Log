package bitc.fullstack503.flightlog.flightlogserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class FlightReservationCheckDTO {

    @JsonProperty("reno")
    private String flightReno;  // 예약 번호

    @JsonProperty("member_lastName")
    private String flightMemberLastName;  // 탑승자 성

    @JsonProperty("member_first_name")
    private String flightMemberFirstName;  // 탑승자 이름

    @JsonProperty("start_city")
    private String flightStartCity;  // 출발 도시

    @JsonProperty("arrival_city")
    private String flightArrivalCity;  // 도착 도시

    @JsonProperty("start_date")
    private String flightReserveStartDate;  // 출발 날짜

    @JsonProperty("start_time")
    private String flightInfoStartTime;  // 출발 시간

    @JsonProperty("arrival_date")
    private String flightReserveEndDate;  // 도착 날짜

    @JsonProperty("arrival_time")
    private String flightInfoArrivalTime;  // 도착 시간

    @JsonProperty("member_num")
    private Integer flightMemberNum;  // 탑승 인원

    @JsonProperty("start_seat_num")
    private String flightMemStartSeatNum;  // 좌석 번호

    @JsonProperty("passport")
    private String flightPassport;  // 여권 번호

    @JsonProperty("luggage")
    private String flightMemLuggage;  // 수하물
}
