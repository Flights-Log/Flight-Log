package bitc.fullstack503.flightlog.flightlogserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class flightInfoDTO {
  @JsonProperty("flightId")
  private int flightId;

  @JsonProperty("flightInfoAirline")
  private String flightInfoAirline;

  @JsonProperty("flightInfoArrivalCity")
  private String flightInfoArrivalCity;

  @JsonProperty("flightInfoArrivalTime")
  private String flightInfoArrivalTime;

  @JsonProperty("flightInfoEddate")
  private String flightInfoEddate;

  @JsonProperty("flightInfoAirlineNum")
  private String flightInfoAirlineNum;

  @JsonProperty("flightInfoStartTime")
  private String flightInfoStartTime;

  @JsonProperty("flightInfoStartDate")
  private String flightInfoStartDate;

  @JsonProperty("flightInfoStartCity")
  private String flightInfoStartCity;

  @JsonProperty("flightMon")
  private String flightMon;

  @JsonProperty("flightTue")
  private String flightTue;

  @JsonProperty("flightWed")
  private String flightWed;

  @JsonProperty("flightThu")
  private String flightThu;

  @JsonProperty("flightFri")
  private String flightFri;

  @JsonProperty("flightSat")
  private String flightSat;

  @JsonProperty("flightSun")
  private String flightSun;
}
