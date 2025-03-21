package bitc.fullstack.FlightLog

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.FlightLog.databinding.ActivityComeAirplaneBinding
import bitc.fullstack.FlightLog.databinding.ActivityGoAirplaneChooseSeatBinding

class GoAirplaneChooseSeatActivity : AppCompatActivity() {
  private val binding: ActivityGoAirplaneChooseSeatBinding by lazy {
    ActivityGoAirplaneChooseSeatBinding.inflate(layoutInflater)
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

  }
}