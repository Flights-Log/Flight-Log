package bitc.fullstack.FlightLog.dto

import com.google.gson.annotations.SerializedName

data class flightReserveMemberDTO(
  @SerializedName("AI")
  var AI: Int,

  @SerializedName("flightPassport")
  var flightPassport: String,

  @SerializedName("flightReno")
  var flightReno: String,

  @SerializedName("flightUserId")
  var flightUserId: String,

  @SerializedName("flightMemFirstName")
  var flightMemFirstName: String,

  @SerializedName("flightMemLastName")
  var flightMemLastName: String,

  @SerializedName("flightMemStartSeatNum")
  var flightMemStartSeatNum: String,

  @SerializedName("flightStartPayCheck")
  var flightStartPayCheck: String,

  @SerializedName("flightArrPayCheck")
  var flightArrPayCheck: String,

  @SerializedName("flightMemLuggage")
  var flightMemLuggage: String,

  @SerializedName("flightMemArriveSeatNum")
  var flightMemArriveSeatNum: String
)