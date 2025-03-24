package bitc.fullstack.FlightLog.appserver

//import bitc.fullstack.app.dto.UserDTO
import bitc.fullstack.FlightLog.data.FlightResponse
import bitc.fullstack.FlightLog.data.InterFlightResponse
import bitc.fullstack.FlightLog.dto.dFlightDTO
import bitc.fullstack.FlightLog.dto.iFlightDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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
  @GET("searchDeparture")
  fun searchDeparture(): Call<List<String>>

  //  도착하는 도시 목록 가져오기
  @GET("searchDestination/{selectedDeparture}")
  fun searchDestination(@Path("selectedDeparture") selectedDeparture: String): Call<List<String>>

}














