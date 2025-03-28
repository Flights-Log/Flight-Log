package bitc.fullstack.FlightLog.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

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
) : Serializable

// 클라이언트 - 서버 응답을 받을 Response DTO
data class LoginResponse(
    val success: Boolean,
    val user: JoinDTO?,
    val message: String?
)