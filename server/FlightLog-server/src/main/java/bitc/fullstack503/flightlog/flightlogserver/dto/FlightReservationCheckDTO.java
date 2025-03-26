package bitc.fullstack503.flightlog.flightlogserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor // 기본 생성자 추가
public class FlightReservationCheckDTO {

    @JsonProperty("flightRene")
    private String flightRene;          // 예약번호

    @JsonProperty("flightStartCity")
    private String flightStartCity;      // 출발 도시

    @JsonProperty("flightArrivalCity")
    private String flightArrivalCity;    // 도착 도시


    @JsonProperty("flightReserveSdate")
    private String flightReserveSdate;      // 출발 날짜

    @JsonProperty("flightIntoStartTime")
    private String flightIntoStartTime;      // 출발 시간

    @JsonProperty("flightMemberNum")
    private Integer flightMemberNum;              // 탑승 인원

    @JsonProperty("flightMemSeatNum")
    private String flightMemSeatNum;            // 좌석번호

    @JsonProperty("flightPassport")
    private String flightPassport;       // 여권번호

    @JsonProperty("flightMemLuggage")
    private String flightMemLuggage;        // 수하물
}
