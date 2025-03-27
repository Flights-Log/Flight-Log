package bitc.fullstack503.flightlog.flightlogserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class flightReserveMemberDTO {
  @JsonProperty("AI")
  private int AI;

  @JsonProperty("flightPassport")
  private String flightPassport;

  @JsonProperty("flightReno")
  private String flightReno;

  @JsonProperty("flightUserId")
  private String flightUserId;

  @JsonProperty("flightMemFirstName")
  private String flightMemFirstName;

  @JsonProperty("flightMemLastName")
  private String flightMemLastName;

  @JsonProperty("flightMemStartSeatNum")
  private String flightMemStartSeatNum;

  @JsonProperty("flightStartPayCheck")
  private String flightStartPayCheck;

  @JsonProperty("flightMemLuggage")
  private String flightMemLuggage;

  @JsonProperty("flightMemArriveSeatNum")
  private String flightMemArriveSeatNum;
}
