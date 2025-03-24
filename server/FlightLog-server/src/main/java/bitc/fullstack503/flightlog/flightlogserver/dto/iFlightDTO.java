package bitc.fullstack503.flightlog.flightlogserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class iFlightDTO {

  @JsonProperty("airlineKorean")
  private String airlineKorean;

  @JsonProperty("arrvcity")
  private String arrvcity;

  @JsonProperty("internationalEddate")
  private String internationalEddate;

  @JsonProperty("internationalMon")
  private String internationalMon;

  @JsonProperty("internationalTue")
  private String internationalTue;

  @JsonProperty("internationalWed")
  private String internationalWed;

  @JsonProperty("internationalThu")
  private String internationalThu;

  @JsonProperty("internationalFri")
  private String internationalFri;

  @JsonProperty("internationalSat")
  private String internationalSat;

  @JsonProperty("internationalSun")
  private String internationalSun;

  @JsonProperty("internationalNum")
  private String internationalNum;

  @JsonProperty("internationalStdate")
  private String internationalStdate;

  @JsonProperty("internationalTime")
  private String internationalTime;

  @JsonProperty("deptcity")
  private String deptcity;



}
