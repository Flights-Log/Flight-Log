package bitc.fullstack503.flightlog.flightlogserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class flightUserDTO {
  @JsonProperty("flightUserId")
  private String flightUserId;

  @JsonProperty("flightUserFirstname")
  private String flightUserFirstname;

  @JsonProperty("flightUserLastname")
  private String flightUserLastname;

  @JsonProperty("flightUserKoFirstname")
  private String flightUserKoFirstname;

  @JsonProperty("flightUserKoLastname")
  private String flightUserKoLastname;

  @JsonProperty("flightUserPw")
  private String flightUserPw;

  @JsonProperty("flightUserBirth")
  private String flightUserBirth;

  @JsonProperty("flightUserPhone")
  private String flightUserPhone;

  @JsonProperty("flightUserGender")
  private String flightUserGender;

  @JsonProperty("flightUserEmail")
  private String flightUserEmail;
}
