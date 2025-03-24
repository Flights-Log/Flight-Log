package bitc.fullstack.FlightLog.dto

import com.google.gson.annotations.SerializedName

// 서버로 전송하는 국내 항공 정보
data class dFlightDTO(
    @SerializedName("airlineKorean")
    var airlineKorean: String,

    @SerializedName("arrivalcity")
    var arrivalcity: String,

    @SerializedName("domesticArrivalTime")
    var domesticArrivalTime: String,

    @SerializedName("domesticEddate")
    var domesticEddate: String,

    @SerializedName("domesticMon")
    var domesticMon: String,

    @SerializedName("domesticTue")
    var domesticTue: String,

    @SerializedName("domesticWed")
    var domesticWed: String,

    @SerializedName("domesticThu")
    var domesticThu: String,

    @SerializedName("domesticFri")
    var domesticFri: String,

    @SerializedName("domesticSat")
    var domesticSat: String,

    @SerializedName("domesticSun")
    var domesticSun: String,

    @SerializedName("domesticNum")
    var domesticNum: String,

    @SerializedName("domesticStdate")
    var domesticStdate: String,

    @SerializedName("domesticStartTime")
    var domesticStartTime: String,

    @SerializedName("startcity")
    var startcity: String
)
