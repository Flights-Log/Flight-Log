package bitc.fullstack.FlightLog

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.FlightLog.R
import bitc.fullstack.FlightLog.databinding.ActivityPassengerBinding

class PassengerActivity : AppCompatActivity() {
  private val binding:ActivityPassengerBinding by lazy{
    ActivityPassengerBinding.inflate(layoutInflater)
  }

  private lateinit var dateEditText: EditText // 날짜를 표시할 edittext
  private lateinit var dateButton: Button     // 날짜 선택 버튼

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(binding.root)
    ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }

//    edittext랑 버튼 연결
    dateEditText = findViewById(R.id.et_date)
    dateButton = findViewById(R.id.btn_birth)

    initEventListener()
  }

  private fun initEventListener(){

//    국적 선택 드롭 다운?
    val spinner: Spinner = findViewById(R.id.nationality)
    val nationalities = arrayOf("대한민국", "미국", "중국", "태국", "베트남")

    val adapter = ArrayAdapter(this,R.layout.spinner_center,nationalities)
    spinner.adapter = adapter

//    생년월일 선택 버튼 이벤트
    binding.btnBirth.setOnClickListener {

//      현재 날짜 기준으로 DatePickerDialog 설정
      val calendar = Calendar.getInstance()
      val year = calendar.get(Calendar.YEAR)
      val month = calendar.get(Calendar.MONTH)
      val day = calendar.get(Calendar.DAY_OF_MONTH)

//      DatePickerDialog 생성
      val datePickerDialog = DatePickerDialog(this, {_,selectedYear, selectedMonth, selectedDayOfMonth ->

//        날짜 선택하면 edittext에 나오게 함
        dateEditText.setText("$selectedYear - ${selectedMonth +1} - $selectedDayOfMonth")}, year,month,day)

//      다이얼로그 띄우기
      datePickerDialog.show()

    }
  }


}