package bitc.fullstack.FlightLog

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.FlightLog.databinding.ActivityMainBinding
import java.time.LocalDate
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
  //  ActivityMainBinding
  private val binding: ActivityMainBinding by lazy {
    ActivityMainBinding.inflate(layoutInflater)
  }

  //  현재 날짜값 및 도착 예정 날짜값
  val goDate = LocalDate.now()
  val comeDate = goDate.plusWeeks(1)

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

//    가는날 오는날 텍스트, 날짜 선택
    chooseGoDate()
    chooseComeDate()

  }

  //  가는 날 텍스트(chooseGoDateText, chooseGoDateArrow) 관련 함수
  fun chooseGoDate() {
//    날짜 출력
    Log.d("flightLog", "goDate : $goDate")
    binding.chooseGoDateText.text = goDate.toString()

//    누르면 캘린더 뷰 나옴
    binding.chooseGoDateText.setOnClickListener {
      val datePickerDialog = DatePickerDialog(
        this,
        { _, year, month, dayOfMonth ->
          // month는 0부터 시작하므로 +1 해줘야 함
          val selectedDate = "$year-${month + 1}-$dayOfMonth"
          binding.chooseGoDateText.text = selectedDate
        },
        goDate.year,
        // LocalDate의 monthValue는 1부터 시작하므로 -1 필요
        goDate.monthValue - 1,
        goDate.dayOfMonth
      )
      datePickerDialog.show()
    }

    binding.chooseGoDateArrow.setOnClickListener {
      val datePickerDialog = DatePickerDialog(
        this,
        { _, year, month, dayOfMonth ->
          // month는 0부터 시작하므로 +1 해줘야 함
          val selectedDate = "$year-${month + 1}-$dayOfMonth"
          binding.chooseGoDateText.text = selectedDate
        },
        goDate.year,
        // LocalDate의 monthValue는 1부터 시작하므로 -1 필요
        goDate.monthValue - 1,
        goDate.dayOfMonth
      )
      datePickerDialog.show()
    }
  }

  //  오는 날 텍스트(chooseComeDateText, chooseComeDateArrow) 관련 함수
  fun chooseComeDate() {
    Log.d("flightLog", "comeDate : $comeDate")
    binding.chooseComeDateText.text = comeDate.toString()

//    누르면 캘린더 뷰 나옴
    binding.chooseComeDateText.setOnClickListener {
      val datePickerDialog = DatePickerDialog(
        this,
        { _, year, month, dayOfMonth ->
          // month는 0부터 시작하므로 +1 해줘야 함
          val selectedDate = "$year-${month + 1}-$dayOfMonth"
          binding.chooseComeDateText.text = selectedDate
        },
        comeDate.year,
        // LocalDate의 monthValue는 1부터 시작하므로 -1 필요
        comeDate.monthValue - 1,
        comeDate.dayOfMonth
      )
      datePickerDialog.show()
    }

    binding.chooseComeDateArrow.setOnClickListener {
      val datePickerDialog = DatePickerDialog(
        this,
        { _, year, month, dayOfMonth ->
          // month는 0부터 시작하므로 +1 해줘야 함
          val selectedDate = "$year-${month + 1}-$dayOfMonth"
          binding.chooseComeDateText.text = selectedDate
        },
        comeDate.year,
        // LocalDate의 monthValue는 1부터 시작하므로 -1 필요
        comeDate.monthValue - 1,
        comeDate.dayOfMonth
      )
      datePickerDialog.show()
    }
  }
}