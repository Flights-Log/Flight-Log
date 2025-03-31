package bitc.fullstack.FlightLog.sidebar

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.FlightLog.databinding.ActivityReservationGoBinding
import bitc.fullstack.FlightLog.databinding.ActivityUnuserReservationBinding
import bitc.fullstack.FlightLog.dto.FlightReservationCheckDTO

class ReservationGoActivity : AppCompatActivity() {

  private val binding: ActivityReservationGoBinding by lazy {
    ActivityReservationGoBinding.inflate(layoutInflater)
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

    val flightMembers =
      intent.getSerializableExtra("flight_data") as? List<FlightReservationCheckDTO>

    if (flightMembers != null && flightMembers.isNotEmpty()) {
      // 데이터가 정상적으로 전달된 경우
      val member = flightMembers[0]  // 첫 번째 항목 가져오기 (한 명만 받은 경우)

      // 로그로 확인
      Log.d("예약 조회 결과", "Flight member: $member")
      Log.d("csy", "Received flight data: $flightMembers")

      // 각 TextView에 데이터 설정
      binding.reservationNumber.setText(member.flightReno)  // 예약 번호
      binding.lastName.setText(member.lastName) // 성
      binding.firstName.setText(member.firstName) // 이름
      binding.seatNumber.setText(member.seatNumber) // 좌석 번호
      binding.passportNumber.setText(member.passport) // 여권 번호
      binding.luggage.setText(member.luggage) //수하물
      binding.startCity.setText(member.startCity) //출발지
      binding.arrivalCity.setText(member.arrivalCity) //도착지
      binding.departureDate.setText(member.departureDate) //출빌날짜
      binding.departureTime.setText(member.departureTime) //출발시간
      binding.arrivalTime.setText(member.arrivalTime) //도착시간
      binding.passengers.setText(member.numPassengers) //도착시간


    } else {
      Log.e("csy", "No flight data received or list is empty")
    }

  }
}


