package bitc.fullstack.FlightLog.dto

import com.google.gson.annotations.SerializedName

// 서버로 전송하는 국내 항공 정보
data class iFlightDTO(

    @SerializedName("airlineKorean")
    var airlineKorean: String,

    @SerializedName("arrvcity")
    var arrvcity: String,

    @SerializedName("internationalEddate")
    var internationalEddate: String,

    @SerializedName("internationalMon")
    var internationalMon: String,

    @SerializedName("internationalTue")
    var internationalTue: String,

    @SerializedName("internationalWed")
    var internationalWed: String,

    @SerializedName("internationalThu")
    var internationalThu: String,

    @SerializedName("internationalFri")
    var internationalFri: String,

    @SerializedName("internationalSat")
    var internationalSat: String,

    @SerializedName("internationalSun")
    var internationalSun: String,

    @SerializedName("internationalNum")
    var internationalNum: String,

    @SerializedName("internationalStdate")
    var internationalStdate: String,

    @SerializedName("internationalTime")
    var internationalTime: String,

    @SerializedName("deptcity")
    var deptcity: String
)
