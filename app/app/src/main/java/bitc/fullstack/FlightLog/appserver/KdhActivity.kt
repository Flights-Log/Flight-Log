package bitc.fullstack.FlightLog.appserver

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.FlightLog.flightchoose.PassengerActivity
import bitc.fullstack.FlightLog.databinding.ActivityKdhBinding
import bitc.fullstack.FlightLog.flightchoose.ReservationCheckActivity
import bitc.fullstack.FlightLog.sidebar.NonmemberActivity


// 만든 창 휴대폰에서 볼라고 만든 임시 Activity
class KdhActivity : AppCompatActivity() {

  private val binding:ActivityKdhBinding by lazy {
    ActivityKdhBinding.inflate(layoutInflater)
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
    binding.btnRc.setOnClickListener {
      val intent = Intent(this, ReservationCheckActivity::class.java)
      startActivity(intent)
    }

    binding.btnNm.setOnClickListener {
      val intent = Intent(this, NonmemberActivity::class.java)
      startActivity(intent)
    }

    binding.btnPsg.setOnClickListener {
      val intent = Intent(this, PassengerActivity::class.java)
      startActivity(intent)
    }

  }


}