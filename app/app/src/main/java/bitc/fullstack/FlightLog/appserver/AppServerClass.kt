package bitc.fullstack.FlightLog.appserver

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

//  Retrofit 의 기본 설정 클래스
object AppServerClass {

 // private val BASE_URL = "http://10.100.203.15:8080/flightLog/"
  private val BASE_URL = "http://10.0.2.2:8080/flightLog/"

  // 실제 디바이스에서 테스트할 때, PC의 IP 주소를 사용해야 함
  // private val BASE_URL = "http://192.168.1.100:8080/flightLog/"

  val instance: AppServerInterface by lazy { Retrofit.Builder()
//      서버 기본 주소
      .baseUrl(BASE_URL)
      //.addConverterFactory(ScalarsConverterFactory.create())
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .create(AppServerInterface::class.java)
  }
}














