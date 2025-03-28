package bitc.fullstack.FlightLog.appserver

//import bitc.fullstack.app.dto.UserDTO
import bitc.fullstack.FlightLog.data.FlightResponse
import bitc.fullstack.FlightLog.data.InterFlightResponse
import bitc.fullstack.FlightLog.dto.dFlightDTO
import bitc.fullstack.FlightLog.dto.flightInfoDTO
import bitc.fullstack.FlightLog.dto.flightUserDTO
import bitc.fullstack.FlightLog.dto.iFlightDTO
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

//  Retrofit 을 사용하여 서버와 통신 시 사용할 메소드 형식 설정
interface AppServerInterface {

  // 국내 항공 스케줄 가져오기, url 매개변수로 키와, 검색날짜, 출력할 개수를 사용
  @GET("getDflightScheduleList")
  fun getFlightInfoXml(
    @Query("ServiceKey") ServiceKey: String,
    @Query("schDate") schDate: String,
    @Query("numOfRows") numOfRows: Int
  ): Call<FlightResponse>

  // 국제 항공 스케줄 가져오기
  @GET("getIflightScheduleList")
  fun getIFlightInfoXml(
    @Query("ServiceKey") ServiceKey: String,
    @Query("schDate") schDate: String,
    @Query("numOfRows") numOfRows: Int
  ): Call<InterFlightResponse>

  @POST("postDFlightInfo")
  fun postDFlightInfo(@Body flightDTOList: MutableList<dFlightDTO>): Call<String>

  @POST("postIFlightInfo")
  fun postIFlightInfo(@Body IflightDTOList: MutableList<iFlightDTO>): Call<String>

  //  출발하는 도시 목록 가져오기
  @GET("main/searchDeparture")
  fun searchDeparture(): Call<List<String>>

  //  도착하는 도시 목록 가져오기
  @GET("main/searchArrive/{selectedDeparture}")
  fun searchArrive(@Path("selectedDeparture") selectedDeparture: String): Call<List<String>>

  //  내가 설정한 출발지, 도착지, 출발일을 기준으로 그 출발일에 운항하는 비행기가 있는지 알아오기
  @GET("main/searchGoAirplane/{startCity}/{arrivalCity}/{goDate}")
  fun searchGoAirplane(
    @Path("startCity") startCity: String,
    @Path("arrivalCity") arrivalCity: String,
    @Path("goDate") goDate: String
  ): Call<List<flightInfoDTO>>

  //  내가 설정한 출발지, 도착지, 도착일을 기준으로 그 도착일에 운항하는 비행기가 있는지 알아오기
  @GET("main/searchComeAirplane/{startCity}/{arrivalCity}/{comeDate}")
  fun searchComeAirplane(
    @Path("startCity") startCity: String,
    @Path("arrivalCity") arrivalCity: String,
    @Path("comeDate") comeDate: String
  ): Call<List<flightInfoDTO>>

  //  roundTripChecked 가 false 일때 가는(편도) 비행기 예약
  @PUT(
    "main" +
            "/reserveGoSeat" +
            "/{flightReno}" +
            "/{userId}" +
            "/{selectedPeople}" +
            "/{goAirplaneFlightId}" +
            "/{goDate}" +
            "/{selectedSeatNames}"
  )
  fun goAirplaneReserveSeat(
    @Path("flightReno") flightReno: String,
    @Path("userId") userId: String,
    @Path("selectedPeople") selectedPeople: Int,
    @Path("goAirplaneFlightId") goAirplaneFlightId: Int,
    @Path("goDate") goDate: String,
    @Path("selectedSeatNames") selectedSeatNames: String
  ): Call<Void>

  //  roundTripChecked 가 True 일때 왕복 비행기 예약
  @PUT(
    "main" +
            "/reserveRoundSeat" +
            "/{flightReno}" +
            "/{userId}" +
            "/{selectedPeople}" +
            "/{goAirplaneFlightId}" +
            "/{goDate}" +
            "/{selectedStartSeatNames}" +
            "/{comeAirplaneFlightId}" +
            "/{comeDate}" +
            "/{selectedArriveSeatNames}"
  )
  fun roundAirplaneReserveSeat(
    @Path("flightReno") flightReno: String,
    @Path("userId") userId: String,
    @Path("selectedPeople") selectedPeople: Int,
    @Path("goAirplaneFlightId") goAirplaneFlightId: Int,
    @Path("goDate") goDate: String,
    @Path("selectedStartSeatNames") selectedStartSeatNames: String,
    @Path("comeAirplaneFlightId") comeAirplaneFlightId: Int,
    @Path("comeDate") comeDate: String,
    @Path("selectedArriveSeatNames") selectedArriveSeatNames: String
  ): Call<Void>

  //  가는 비행기의 예약된 좌석 목록 가져오기
  @GET("main/goAirplaneIsSeatReservated/{goAirplaneFlightId}")
  fun goAirplaneIsSeatReservated(
    @Path("goAirplaneFlightId") goAirplaneFlightId: Int
  ): Call<List<String>>

  //  오는 비행기의 예약된 좌석 목록 가져오기
  @GET("main/comeAirplaneIsSeatReservated/{comeAirplaneFlightId}")
  fun comeAirplaneIsSeatReservated(
    @Path("comeAirplaneFlightId") comeAirplaneFlightId: Int
  ): Call<List<String>>

  //  현재 로그인한 유저의 성, 이름 가져오기
  @GET("main/getUserName/{userId}")
  fun getUserName(
    @Path("userId") userId: String
  ): Call<List<flightUserDTO>>

  //  가는(편도) 비행기 예매할 때
  @PUT("main/reserveGoAirplaneMember/{passport}/{flightReno}/{userId}/{firstName}/{lastName}/{selectedSeatName}/{startSeatPrice}/{luggage}")
  fun reserveGoAirplaneMember(
    @Path("passport") passport: String,
    @Path("flightReno") flightReno: String,
    @Path("userId") userId: String,
    @Path("firstName") firstName: String,
    @Path("lastName") lastName: String,
    @Path("selectedSeatName") selectedSeatName: String,
    @Path("startSeatPrice") startSeatPrice: Int,
    @Path("luggage") luggage: String
  ): Call<Void>

  //  혼자서 왕복 비행기 예매할 때
  @PUT("main/reserveRoundAirplaneMember/{passport}/{roundFlightReno}/{userId}/{firstName}/{lastName}/{selectedStartSeatName}/{startSeatPrice}/{luggage}/{selectedArriveSeatName}/{arriveSeatPrice}")
  fun reserveRoundAirplaneMember(
    @Path("passport") passport: String,
    @Path("roundFlightReno") roundFlightReno: String,
    @Path("userId") userId: String,
    @Path("firstName") firstName: String,
    @Path("lastName") lastName: String,
    @Path("selectedStartSeatName") selectedStartSeatName: String,
    @Path("startSeatPrice") startSeatPrice: Int,
    @Path("luggage") luggage: String,
    @Path("selectedArriveSeatName") selectedArriveSeatName: String,
    @Path("arriveSeatPrice") arriveSeatPrice: Int
  ): Call<Void>

  //  다른 날짜 추천
  @GET("main/recommendStartDate/{startCity}/{arrivalCity}")
  fun recommendStartDate(
    @Path("startCity") startCity: String,
    @Path("arrivalCity") arrivalCity: String
  ): Call<ResponseBody>
}