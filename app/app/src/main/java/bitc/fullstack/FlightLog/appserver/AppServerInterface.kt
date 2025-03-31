package bitc.fullstack.FlightLog.appserver

//import bitc.fullstack.app.dto.UserDTO
import bitc.fullstack.FlightLog.data.FlightResponse
import bitc.fullstack.FlightLog.data.InterFlightResponse
import bitc.fullstack.FlightLog.dto.JoinDTO
import bitc.fullstack.FlightLog.dto.LoginResponse
import bitc.fullstack.FlightLog.dto.dFlightDTO
import bitc.fullstack.FlightLog.dto.iFlightDTO
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

//  Retrofit 을 사용하여 서버와 통신 시 사용할 메소드 형식 설정
interface AppServerInterface {

  // 국내 항공 스케줄 가져오기, url 매개변수로 키와, 검색날짜, 출력할 개수를 사용
  @GET("getDflightScheduleList")
  fun getFlightInfoXml(
    @Query("ServiceKey") ServiceKey : String,
    @Query("schDate") schDate : String,
    @Query("numOfRows") numOfRows : Int
  ): Call<FlightResponse>

  // 국제 항공 스케줄 가져오기
  @GET("getIflightScheduleList")
  fun getIFlightInfoXml(
    @Query("ServiceKey") ServiceKey : String,
    @Query("schDate") schDate : String,
    @Query("numOfRows") numOfRows : Int
  ): Call<InterFlightResponse>

  @POST("postDFlightInfo")
  fun postDFlightInfo(@Body flightDTOList: MutableList<dFlightDTO>): Call<String>

  @POST("postIFlightInfo")
  fun postIFlightInfo(@Body IflightDTOList: MutableList<iFlightDTO>): Call<String>


  //  회원가입 입력 전송하기
  @POST("joinMember")
  fun joinMember(@Body joinDTO: JoinDTO): Call<String>

  // 로그인 요청
  @POST("flightLogin")
  @FormUrlEncoded
  fun flightLogin(@Field("inputUserId") inputUserId:String,
                  @Field("inputUserPw") inputUserPw:String): Call<LoginResponse>

  @POST("updateUser")
  fun updateUserInfo(
    @Body user: JoinDTO
  ):Call<List<JoinDTO>>


  @POST("userInfo")
  @FormUrlEncoded
  fun userInfo
            (@Field ("userId") userId : String,
  ):Call<List<JoinDTO>>
}














