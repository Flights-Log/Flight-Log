package bitc.fullstack.FlightLog.dto

import com.google.gson.annotations.SerializedName

data class flightUserDTO(
  @SerializedName("flightUserId")
  var flightUserId: String,

  @SerializedName("flightUserFirstname")
  var flightUserFirstname: String,

  @SerializedName("flightUserLastname")
  var flightUserLastname: String,

  @SerializedName("flightUserKoFirstname")
  var flightUserKoFirstname: String,

  @SerializedName("flightUserKoLastname")
  var flightUserKoLastname: String,

  @SerializedName("flightUserPw")
  var flightUserPw: String,

  @SerializedName("flightUserBirth")
  var flightUserBirth: String,

  @SerializedName("flightUserPhone")
  var flightUserPhone: String,

  @SerializedName("flightUserGender")
  var flightUserGender: String,

  @SerializedName("flightUserEmail")
  var flightUserEmail: String
)