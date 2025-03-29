package bitc.fullstack.FlightLog.flightchoose

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.FlightLog.R
import bitc.fullstack.FlightLog.appserver.AppServerClass
import bitc.fullstack.FlightLog.databinding.ActivityReservationCheckBinding
import bitc.fullstack.FlightLog.dto.FlightReservationCheckDTO
import bitc.fullstack.FlightLog.flightmain.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReservationCheckActivity : AppCompatActivity() {

  private val binding: ActivityReservationCheckBinding by lazy{
    ActivityReservationCheckBinding.inflate(layoutInflater)
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

  private fun initEventListener() {

    binding.btnHome.setOnClickListener {
      val intent = Intent(this, MainActivity::class.java)
      startActivity(intent)
    }


//    val roundTrip = intent.getBooleanExtra("왕복", false)
//
//    val returnTicket = binding.returnTicket
//    if (roundTrip) {
//      returnTicket.visibility = View.VISIBLE  // 왕복이면 보이기
//    } else {
//      returnTicket.visibility = View.GONE  // 편도면 숨기기
//    }
//
//    binding.btnReturnCheck.setOnClickListener {
//      val oneWay = returnTicket.visibility
//      if (oneWay == View.VISIBLE) {
//        returnTicket.visibility = View.GONE  // 숨김
//      } else {
//        returnTicket.visibility = View.VISIBLE  // 보임
//      }
//    }


//    val reno = binding.reservationNumber.text.toString()

    val reservationNumber = "testNo1"
    val api = AppServerClass.instance
    //      DTO 타입을 서버로 전달
    val call = api.getReservationCheck(reservationNumber)
    retrofitResponse(call)
  }

  private fun retrofitResponse(call: Call<List<FlightReservationCheckDTO>>) {

    call.enqueue(object : Callback<List<FlightReservationCheckDTO>> {
      override fun onResponse(p0: Call<List<FlightReservationCheckDTO>>, res: Response<List<FlightReservationCheckDTO>>) {
        if (res.isSuccessful) {
          val result = res.body()
          Log.d("csy", "result : $result")

//          if (result != null) {
//            // flightArrId가 null인 경우를 체크
//            val hasReturnFlight = result.any { it.flightArrId != null }
//
//            val intent = Intent(this@ReservationCheckActivity, ReservationCheckActivity::class.java)

//            // flightArrId가 null인 경우 다른 화면으로 전환
//            if (hasReturnFlight) {
//              // 왕복 비행편인 경우
//              intent.putExtra("flight_data", ArrayList(result))
//              startActivity(intent)
//            } else {
//              // 편도 비행편인 경우
//              // 다른 화면을 보여주고, 그 화면에 맞는 Activity로 전환
//              val oneWayIntent = Intent(this@OneWayReservationActivity, OneWayReservationActivity::class.java)
//              oneWayIntent.putExtra("flight_data", ArrayList(result))
//              startActivity(oneWayIntent)
//            }
//          }
//
//          else {
//            Log.e("csy", "Result is null!")
//          }


          if (result != null && result.isNotEmpty()) {
            updateUI(result[0])  // 첫 번째 예약 데이터를 UI에 반영
          }

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

      override fun onFailure(p0: Call<List<FlightReservationCheckDTO>>, t: Throwable) {
        Log.d("csy", "message : $t.message")
      }
    })
  }

  private fun updateUI(reservation: FlightReservationCheckDTO) {
    binding.reservationNumber.setText(reservation.flightReno)
    binding.lastName.setText(reservation.lastName)
    binding.firstName.setText(reservation.firstName)
    binding.startCity.setText(reservation.startCity)
    binding.arrivalCity.setText(reservation.arrivalCity)
    binding.departureDate.setText(reservation.departureDate)
    binding.departureTime.setText(reservation.departureTime)
    binding.arrivalTime.setText(reservation.arrivalTime)
    binding.passengers.setText(reservation.numPassengers.toString())
    binding.seatNumber.setText(reservation.seatNumber)
    binding.passportNumber.setText(reservation.passport)
    binding.luggage.setText(reservation.luggage)

    binding.returnReservationNumber.setText(reservation.flightReno)
    binding.returnLastName.setText(reservation.lastName)
    binding.returnFirstName.setText(reservation.firstName)
    binding.returnStartCity.setText(reservation.returnStartCity)
    binding.returnArrivalCity.setText(reservation.returnArrivalCity)
    binding.returnDepartureDate.setText(reservation.returnDepartureDate)
    binding.returnDepartureTime.setText(reservation.returnDepartureTime)
    binding.returnArrivalTime.setText(reservation.returnArrivalTime)
    binding.returnPassengers.setText(reservation.numPassengers.toString())
    binding.returnSeatNumber.setText(reservation.returnSeatNumber)
    binding.returnPassportNumber.setText(reservation.passport)
    binding.returnLuggage.setText(reservation.luggage)

  }
}