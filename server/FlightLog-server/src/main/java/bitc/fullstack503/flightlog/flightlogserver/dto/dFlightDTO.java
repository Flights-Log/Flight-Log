package bitc.fullstack503.flightlog.flightlogserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class dFlightDTO {

  @JsonProperty("airlineKorean")
  private String airlineKorean;

  @JsonProperty("arrivalcity")
  private String arrivalcity;

  @JsonProperty("domesticArrivalTime")
  private String domesticArrivalTime;

  @JsonProperty("domesticEddate")
  private String domesticEddate;

  @JsonProperty("domesticMon")
  private String domesticMon;

  @JsonProperty("domesticTue")
  private String domesticTue;

  @JsonProperty("domesticWed")
  private String domesticWed;

  @JsonProperty("domesticThu")
  private String domesticThu;

  @JsonProperty("domesticFri")
  private String domesticFri;

  @JsonProperty("domesticSat")
  private String domesticSat;

  @JsonProperty("domesticSun")
  private String domesticSun;

  @JsonProperty("domesticNum")
  private String domesticNum;

  @JsonProperty("domesticStdate")
  private String domesticStdate;

  @JsonProperty("domesticStartTime")
  private String domesticStartTime;

  @JsonProperty("startcity")
  private String startcity;



}
