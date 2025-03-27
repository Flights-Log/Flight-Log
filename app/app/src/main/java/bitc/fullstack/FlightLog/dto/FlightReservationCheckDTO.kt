package bitc.fullstack.FlightLog.dto

import com.google.gson.annotations.SerializedName

data class FlightReservationCheckDTO(

    @SerializedName("reno")
    val flightReno: String,  // 예약 번호

    @SerializedName("member_lastName")
    val flightMemberLastName: String,  // 탑승자 성

    @SerializedName("member_first_name")
    val flightMemberFirstName: String,  // 탑승자 이름

    @SerializedName("start_city")
    val flightStartCity: String,  // 출발 도시

    @SerializedName("arrival_city")
    val flightArrivalCity: String,  // 도착 도시

    @SerializedName("start_date")
    val flightReserveStartDate: String,  // 출발 날짜

    @SerializedName("start_time")
    val flightInfoStartTime: String,  // 출발 시간

    @SerializedName("arrival_date")
    val flightReserveEndDate: String,  // 도착 날짜

    @SerializedName("arrival_time")
    val flightInfoArrivalTime: String,  // 도착 시간

    @SerializedName("member_num")
    val flightMemberNum: Int,  // 탑승 인원

    @SerializedName("start_seat_num")
    val flightMemStartSeatNum: String,  // 좌석 번호

    @SerializedName("passport")
    val flightPassport: String,  // 여권 번호

    @SerializedName("luggage")
    val flightMemLuggage: String  // 수하물
)
