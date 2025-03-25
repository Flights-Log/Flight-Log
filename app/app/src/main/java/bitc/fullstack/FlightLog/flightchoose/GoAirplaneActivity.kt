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
import bitc.fullstack.FlightLog.databinding.ActivityGoAirplaneBinding

class GoAirplaneActivity : AppCompatActivity() {
  //  ActivityGoAirplaneBinding
  private val binding: ActivityGoAirplaneBinding by lazy {
    ActivityGoAirplaneBinding.inflate(layoutInflater)
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

    binding.textStartCity.text = intent.getStringExtra("출발지")
    binding.textArrivalCity.text = intent.getStringExtra("도착지")
    binding.flightDepartureDate.text = intent.getStringExtra("출발일")

    Log.d("flightLog", "받은 출발지 : ${intent.getStringExtra("출발지")}")
    Log.d("flightLog", "받은 도착지 : ${intent.getStringExtra("도착지")}")
    Log.d("flightLog", "받은 출발일 : ${intent.getStringExtra("출발일")}")
    Log.d("flightLog", "받은 도착일 : ${intent.getStringExtra("도착일")}")

  }
}