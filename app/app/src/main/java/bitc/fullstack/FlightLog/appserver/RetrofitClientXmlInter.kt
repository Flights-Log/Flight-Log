package bitc.fullstack.FlightLog.appserver

import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

// Retrofit 라이브러리를 사용하여 실제 url과 통신
object RetrofitClientXmlInter {
    private val BASE_URL = "http://openapi.airport.co.kr/service/rest/FlightScheduleList/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY  // 요청과 응답 본문을 로그로 출력
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val instance : AppServerInterface by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
            .create(AppServerInterface::class.java)
    }
}