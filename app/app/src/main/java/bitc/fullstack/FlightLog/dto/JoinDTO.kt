package bitc.fullstack.FlightLog.dto

import com.google.gson.annotations.SerializedName

data class JoinDTO(

    @SerializedName("flightUserId")
    val flightUserId: String,
    @SerializedName("flightUserEmail")
    val flightUserEmail: String,
    @SerializedName("flightUserPw")
    val flightUserPw: String,
    @SerializedName("flightUserFirstname")
    val flightUserFirstname: String,
    @SerializedName("flightUserLastname")
    val flightUserLastname: String,
    @SerializedName("flightUserKoFirstname")
    val flightUserKoFirstname: String,
    @SerializedName("flightUserKoLastname")
    val flightUserKoLastname: String,
    @SerializedName("flightUserPhone")
    val flightUserPhone: String,
    @SerializedName("flightUserGender")
    val flightUserGender: String,
    @SerializedName("flightUserBirth")
    val flightUserBirth: String
)
