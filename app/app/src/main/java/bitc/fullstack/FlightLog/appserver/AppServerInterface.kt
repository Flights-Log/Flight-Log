package bitc.fullstack.app

//import bitc.fullstack.app.dto.UserDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

//  Retrofit 을 사용하여 서버와 통신 시 사용할 메소드 형식을 미리 설정
interface AppServerInterface {

  @GET("gettest1")
  fun getTest1(): Call<String>

}














