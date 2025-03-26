package bitc.fullstack.FlightLog.flightchoose

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.FlightLog.R
import bitc.fullstack.FlightLog.databinding.ActivityComeAirplaneBinding

//  출발도시, 도착도시, 가는날
private var selectedDeparture: String = ""
private var selectedArrive: String = ""
private var goDate: String = ""
private var comeDate: String = ""
private var selectedPeople: Int = 0
private var selectedSeat: Int = 0
private var distance: Double = 0.0

//각 좌석의 가격
private const val firstSeatPrice = 1500
private const val businessSeatPrice = 1000
private const val regularSeatPrice = 500

//가는 비행기 좌석 총 경비
private var goAirplaneTotalPrice = 0

//한국 통화 형식으로 환산
private var formattedGoAirplane = ""

//가는 비행기 좌석 총 경비
private var comeAirplaneTotalPrice = 0

//한국 통화 형식으로 환산
private var formattedComeAirplane = ""

class ComeAirplaneActivity : AppCompatActivity() {
  private val binding: ActivityComeAirplaneBinding by lazy {
    ActivityComeAirplaneBinding.inflate(layoutInflater)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(binding.root)

    //    툴바 아이콘
    val menuButton: ImageButton = findViewById(R.id.flight_log_menu)
    val iconButton: ImageView = findViewById(R.id.flight_log_icon)
    val loginButton: TextView = findViewById(R.id.flight_log_login)

    ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }

    //    각 변수에 intent 에서 넘어온 값 대입
    selectedDeparture = intent.getStringExtra("출발지").toString()
    selectedArrive = intent.getStringExtra("도착지").toString()
    goDate = intent.getStringExtra("출발일").toString()
    comeDate = intent.getStringExtra("도착일").toString()
    selectedPeople = intent.getIntExtra("인원수", 1)
    distance = intent.getDoubleExtra("거리", 0.0)
    goAirplaneTotalPrice = intent.getIntExtra("가는 비행기 총 비용", 0)

//    확인용
    Log.d("flightLog", "selectedDeparture = $selectedDeparture")
    Log.d("flightLog", "selectedArrive = $selectedArrive")
    Log.d("flightLog", "goDate = $goDate")
    Log.d("flightLog", "comeDate = $comeDate")
    Log.d("flightLog", "selectedPeople = $selectedPeople")
    Log.d("flightLog", "distance = $distance")
    Log.d("flightLog", "goAirplaneTotalPrice = $goAirplaneTotalPrice")

  }
}