package bitc.fullstack.FlightLog

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.FlightLog.databinding.ActivityMainBinding
import bitc.fullstack.FlightLog.databinding.ActivitySelectPeopleBinding

class SelectPeopleActivity : AppCompatActivity() {
  //  ActivityMainBinding
  private val binding: ActivitySelectPeopleBinding by lazy {
    ActivitySelectPeopleBinding.inflate(layoutInflater)
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