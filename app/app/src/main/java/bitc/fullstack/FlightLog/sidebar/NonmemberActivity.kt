package bitc.fullstack.FlightLog.sidebar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.FlightLog.appserver.AppServerClass
import bitc.fullstack.FlightLog.data.InterFlightResponse
import bitc.fullstack.FlightLog.databinding.ActivityNonMemberBinding
import bitc.fullstack.FlightLog.dto.FlightReservationCheckDTO
import bitc.fullstack.FlightLog.dto.iFlightDTO
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NonmemberActivity : AppCompatActivity() {
  private val binding:ActivityNonMemberBinding by lazy{
    ActivityNonMemberBinding.inflate(layoutInflater)
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

    initEventListener()
  }

  private fun initEventListener(){
    binding.unuserSearch.setOnClickListener {
      Log.d("flightLog","unuser search")

      // 예약번호
      val inputText = binding.nonmemberReservationNumber.text.toString()
      // 여권번호
      val inputText1 = binding.nonmemberPassnum.text.toString()

      val api = AppServerClass.instance
      val call = api.postUnuserSearch(inputText, inputText1)
      retrofitResponse(call)
    }
  }

  //  Retrofit 통신 응답 부분을 따로 메소드로 분리
  private fun retrofitResponse(call: Call<List<FlightReservationCheckDTO>>) {

    call.enqueue(object : Callback<List<FlightReservationCheckDTO>>{
      override fun onResponse(p0: Call<List<FlightReservationCheckDTO>>, res: Response<List<FlightReservationCheckDTO>>) {
          if (res.isSuccessful) {
              val result = res.body()
              Log.d("csy", "result : $result")

              if (result != null) {
                  // flightArrId가 null인 경우를 체크 it.flightArrId != null &&
                  val hasReturnFlight = result.any { it.flightArrId != 0 }

                  // flightArrId가 null인 경우 다른 화면으로 전환
                  if (hasReturnFlight) {
                      // 왕복 비행편인 경우
                      // 다른 화면을 보여주고, 그 화면에 맞는 Activity로 전환
                      val intent = Intent(this@NonmemberActivity, UnuserReservationActivity::class.java)
                      intent.putExtra("flight_data", ArrayList(result))
                      startActivity(intent)
                  } else {
                      // 편도 비행편인 경우
                      val intent  = Intent(this@NonmemberActivity, ReservationGoActivity::class.java)
                      intent .putExtra("flight_data", ArrayList(result))
                      startActivity(intent )
                  }
              } else {
                  Log.e("csy", "Result is null!")
              }
          }
      }
      override fun onFailure(p0: Call<List<FlightReservationCheckDTO>>, t: Throwable) {
        Log.d("csy", "message : $t.message")
      }
    })
  }
}
