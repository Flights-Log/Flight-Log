package bitc.fullstack.FlightLog.sidebar

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.FlightLog.R
import bitc.fullstack.FlightLog.databinding.ActivityPassengerInfoBinding

class PassengerInfoActivity : AppCompatActivity() {

  private val binding: ActivityPassengerInfoBinding by lazy {
    ActivityPassengerInfoBinding.inflate(layoutInflater)
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

    val genderSpinner: Spinner = binding.genderSpinner
    val adapter = ArrayAdapter.createFromResource(
      this,
      R.array.gender_array, // strings.xml에서 정의한 gender_array 배열
      android.R.layout.simple_spinner_item
    )
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    genderSpinner.adapter = adapter
  }
}