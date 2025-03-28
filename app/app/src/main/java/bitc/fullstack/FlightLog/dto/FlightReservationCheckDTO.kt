package bitc.fullstack.FlightLog.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FlightReservationCheckDTO(

    // 가는 DTO - 여권 번호
    var passport: String,

    var flightReno: String,//예약 번호

    var firstName: String, //승객 이름

    var lastName: String, //승객 성

    var startCity:String, // 출발지

    var arrivalCity:String, // 도착지

    var departureDate:String, // 도착 날짜

    var departureTime:String, // 도착 시간

    var arrivalTime:String, // 도착 시간

    var numPassengers:String, // 승객 수

    var seatNumber: String,// 좌석 번호

    var flightStartPayCheck: String,//

    var flightArrPayCheck: String,

    var luggage: String,// 수하물

    var flightArrId: Int, //


    //    오는날
    var returnStartCity: String, //출발지

    var returnArrivalCity:String,//도착지

    var returnDepartureDate:String,//출발 날짜

    var returnDepartureTime:String,//출발 시간

    var returnArrivalTime:String,//도착 시간

    var returnSeatNumber:String,//좌석 번호

) : Serializable