package bitc.fullstack.FlightLog.dto

import com.google.gson.annotations.SerializedName

data class flightInfoDTO(
  @SerializedName("flightId")
  var flightId: Int,

  @SerializedName("flightInfoAirline")
  var flightInfoAirline: String,

  @SerializedName("flightInfoArrivalCity")
  var flightInfoArrivalCity: String,

  @SerializedName("flightInfoArrivalTime")
  var flightInfoArrivalTime: String,

  @SerializedName("flightInfoEddate")
  var flightInfoEddate: String,

  @SerializedName("flightInfoAirlineNum")
  var flightInfoAirlineNum: String,

  @SerializedName("flightInfoStartTime")
  var flightInfoStartTime: String,

  @SerializedName("flightInfoStartDate")
  var flightInfoStartDate: String,

  @SerializedName("flightInfoStartCity")
  var flightInfoStartCity: String,

  @SerializedName("flightMon")
  var flightMon: String,

  @SerializedName("flightTue")
  var flightTue: String,

  @SerializedName("flightWed")
  var flightWed: String,

  @SerializedName("flightThu")
  var flightThu: String,

  @SerializedName("flightFri")
  var flightFri: String,

  @SerializedName("flightSat")
  var flightSat: String,

  @SerializedName("flightSun")
  var flightSun: String
)