package bitc.fullstack.FlightLog

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.FlightLog.appserver.AppServerClass
import bitc.fullstack.FlightLog.appserver.RetrofitClientXml
import bitc.fullstack.FlightLog.data.FlightResponse
import bitc.fullstack.FlightLog.data.InterFlightResponse
import bitc.fullstack.FlightLog.databinding.ActivityFlightInfoApiactivityBinding
import bitc.fullstack.FlightLog.dto.dFlightDTO
import bitc.fullstack.FlightLog.dto.iFlightDTO
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FlightInfoAPIActivity : AppCompatActivity() {

    private val binding: ActivityFlightInfoApiactivityBinding by lazy {
        ActivityFlightInfoApiactivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnFlightInfo.setOnClickListener{
            val flightDTOList = mutableListOf<dFlightDTO>()
            val api = RetrofitClientXml.instance
            val call = api.getFlightInfoXml(
                ServiceKey = "XSio3dSgHiYVaYwQLFud+un3+/rs2m6X2fjxz5Zu4xhbffDGtaJNViHXK8K+2b5Qra8uLwTfmAqh56kdjrpZxg==",  // 실제 서비스 키
                schDate = "20240101",  // 실제 검색 날짜
                numOfRows = 621
            )

            // 통신 결과 가져옴 - 성공시
            call.enqueue(object : Callback<FlightResponse>{
                //        통신 성공 시
                override fun onResponse(call: Call<FlightResponse>, res: Response<FlightResponse>) {
                    if (res.isSuccessful) {
                        //Log.d("fullstack503", "응답 본문: ${res.body()?.toString()}")
//            서버에서 받아온 데이터를 출력
                        val flightResponse = res.body()  // FlightResponse 객체를 받음
                        //Log.d("fullstack503", "응답: ${flightResponse}")
                        val flightBody = flightResponse?.flightbody  // body 안의 FlightBody 객체
                        val flightInfoList = flightBody?.flightItems  // FlightBody 안의 items 리스트
                       // Log.d("fullstack503", "Items: ${flightInfoList}")

                        if (flightInfoList.isNullOrEmpty()) {
                            Log.d("fullstack503", "항공편 정보가 없습니다.")
                        } else {
                            for (item in flightInfoList) {
                                // 필요하면 로그를 주석처리하거나 특정 조건에서만 출력
                                Log.d("fullstack503", "항공사명 : ${item.airlineKorean}, 항공편명 : ${item.domesticNum}, 출발 지역 : ${item.startcity}, 도착 지역 : ${item.arrivalcity}, 출발시간 : ${item.domesticStartTime}, 도착시간 : ${item.domesticArrivalTime} ")

                                // dFlightDTO 객체 생성
                                val dflightInfo:dFlightDTO = dFlightDTO(
                                    airlineKorean = item.airlineKorean ?: "",
                                    arrivalcity = item.arrivalcity ?: "",
                                    domesticArrivalTime = item.domesticArrivalTime ?: "",
                                    domesticEddate = item.domesticEddate ?: "",
                                    domesticMon = item.domesticMon ?: "",
                                    domesticTue = item.domesticTue ?: "",
                                    domesticWed = item.domesticWed ?: "",
                                    domesticThu = item.domesticThu ?: "",
                                    domesticFri = item.domesticFri ?: "",
                                    domesticSat = item.domesticSat ?: "",
                                    domesticSun = item.domesticSun ?: "",
                                    domesticNum = item.domesticNum ?: "",
                                    domesticStdate = item.domesticStdate ?: "",
                                    domesticStartTime = item.domesticStartTime ?: "",
                                    startcity = item.startcity ?: ""
                                )
                                //Log.d("csy", "전송할 데이터: $dflightInfo")

                                // DTO 리스트에 추가
                                flightDTOList.add(dflightInfo)
                            }

                            Log.d("RequestFlightInfo", "송신 데이터: ${Gson().toJson(flightDTOList)}")
                            val api2 = AppServerClass.instance
                            //      DTO 타입을 서버로 전달
                            val call2 = api2.postDFlightInfo(flightDTOList)
                            retrofitResponse(call2)

                        }
                    }
                    else {
                        Log.d("fullstack503", "응답 실패 : ${res.errorBody()?.string()}")
                    }
                }

                //        통신 실패 시
                override fun onFailure(call: Call<FlightResponse>, t: Throwable) {
                    Log.d("fullstack503", "서버 요청 실패 : ${t.message}")
                }

            })
        }


        // 국제 운항 정보 가져오기
        binding.btnInterFlightInfo.setOnClickListener{
            val IflightDTOList = mutableListOf<iFlightDTO>()
            val api = RetrofitClientXml.instance
            val call = api.getIFlightInfoXml(
                ServiceKey = "XSio3dSgHiYVaYwQLFud+un3+/rs2m6X2fjxz5Zu4xhbffDGtaJNViHXK8K+2b5Qra8uLwTfmAqh56kdjrpZxg==",  // 실제 서비스 키
                schDate = "20240331",  // 실제 검색 날짜
                numOfRows = 398
            )

            // 통신 결과 가져옴 - 성공시
            call.enqueue(object : Callback<InterFlightResponse>{
                //        통신 성공 시
                override fun onResponse(call: Call<InterFlightResponse>, res: Response<InterFlightResponse>) {
                    if (res.isSuccessful) {
                        Log.d("fullstack503", "응답 본문: ${res.body()?.toString()}")

                        // 서버에서 받아온 데이터를 출력
                        val flightResponse = res.body()
                        val flightBody = flightResponse?.interflightbody
                        val flightInfoList = flightBody?.InterflightItems

                        if (flightInfoList.isNullOrEmpty()) {
                            Log.d("fullstack503", "국제 항공편 정보가 없습니다.")
                        } else {
                            for (item in flightInfoList) {
                                // 필요하면 로그를 주석처리하거나 특정 조건에서만 출력
                                Log.d("fullstack503", "항공사명 : ${item.airlineKorean}, 항공편명 : ${item.internationalNum}, 출발 지역 : ${item.deptcity}, 도착 지역 : ${item.arrvcity}, 출발시간 : ${item.internationalTime} ")

                                // dFlightDTO 객체 생성
                                val iflightInfo:iFlightDTO = iFlightDTO(
                                    airlineKorean = item.airlineKorean ?: "",
                                    arrvcity = item.arrvcity ?: "",
                                    internationalEddate = item.internationalEddate ?: "",
                                   // domesticEddate = item.domesticEddate ?: "",
                                    internationalMon = item.internationalMon ?: "",
                                    internationalTue = item.internationalTue ?: "",
                                    internationalWed = item.internationalWed ?: "",
                                    internationalThu = item.internationalThu ?: "",
                                    internationalFri = item.internationalFri ?: "",
                                    internationalSat = item.internationalSat ?: "",
                                    internationalSun = item.internationalSun ?: "",
                                    internationalNum = item.internationalNum ?: "",
                                    internationalStdate = item.internationalStdate ?: "",
                                    internationalTime = item.internationalTime ?: "",
                                    deptcity = item.deptcity ?: ""
                                )

                                // DTO 리스트에 추가
                                IflightDTOList.add(iflightInfo)
                            }

                            Log.d("RequestFlightInfo", "송신 데이터: ${Gson().toJson(IflightDTOList)}")
                            val api2 = AppServerClass.instance
                            //      DTO 타입을 서버로 전달
                            val call2 = api2.postIFlightInfo(IflightDTOList)
                            retrofitResponse(call2)

                        }
                    }
                    else {
                        Log.d("fullstack503", "응답 실패 : ${res.errorBody()?.string()}")
                    }
                }

                //        통신 실패 시
                override fun onFailure(call: Call<InterFlightResponse>, t: Throwable) {
                    Log.d("fullstack503", "서버 요청 실패 : ${t.message}")
                }

            })

        }





    }

    //  Retrofit 통신 응답 부분을 따로 메소드로 분리
    private fun retrofitResponse(call: Call<String>) {

        call.enqueue(object : Callback<String>{
            override fun onResponse(p0: Call<String>, res: Response<String>) {
                if (res.isSuccessful) {
                    val result = res.body()
                    Log.d("csy", "result : $result")
                }
                else {
                    Log.d("csy", "송신 실패, 상태 코드: ${res.code()}, 메시지: ${res.message()}")
                    //Log.d("csy", "송신 실패")
                    res.errorBody()?.let { errorBody ->
                        val error = errorBody.string()
                        Log.d("csy", "Error Response: $error")
                    }
                }
            }

            override fun onFailure(p0: Call<String>, t: Throwable) {
                Log.d("csy", "message : $t.message")
            }
        })
    }
}