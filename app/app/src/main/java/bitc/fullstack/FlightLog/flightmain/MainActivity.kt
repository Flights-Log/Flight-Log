package bitc.fullstack.FlightLog.flightmain

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.FlightLog.R
import bitc.fullstack.FlightLog.appserver.AppServerClass
import bitc.fullstack.FlightLog.databinding.ActivityMainBinding
import bitc.fullstack.FlightLog.flightchoose.GoAirplaneActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.util.Calendar

class MainActivity : AppCompatActivity(),
  SelectPeopleDialogFragment.OnPassengerSelectedListener,
  OnDepartureSelectedListener,
  OnArriveSelectedListener {

  //  ActivityMainBinding
  private val binding: ActivityMainBinding by lazy {
    ActivityMainBinding.inflate(layoutInflater)
  }

  //  현재 날짜값 및 도착 예정 날짜값
  private var goDate = LocalDate.now()
  private var comeDate = goDate.plusWeeks(1)

  //  출발지 및 도착지
  private var selectedDeparture: String = ""
  private var selectedArrive: String = ""

  //  출발지와 도착지를 바꿀 때 임시로 저장할 string 객체 하나
  private var tempLocation: String = ""

  private var selectedPeople: Int = 1

  //  만들어지만 할거
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

//    인원 선택 창으로 가는 함수
    chooseSelectPeople()

//    출발지 설정
    chooseDeparture()

//    도착지 설정
    chooseArrive()

//    출발지와 도착지를 바꾸기
    changeDestinationArrive()

//    조회하기 버튼
    searchFlight()
  }

  //  가는 날 텍스트(chooseGoDateText) 관련 함수
  fun chooseGoDate() {
//    날짜 출력
    binding.chooseGoDateText.text = goDate.toString()
    Log.d("flightLog", "바꾸기 전 goDate : $goDate")

//    누르면 캘린더 뷰 나옴
    binding.chooseGoDateText.setOnClickListener {
//      현재 시간기반 달력 얻어오기
      val calendar = Calendar.getInstance()

//      날짜 선택하는 상자 만들기
      val datePickerDialog = DatePickerDialog(
        this,
//        LocalDate 에서 현재 기기의 연월일 가져오기
        { _, year, month, dayOfMonth ->
          val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)

//          가는 날 변경 시 오는 날도 최소 한 주 뒤로 자동 업데이트
          goDate = selectedDate
          if (comeDate.isBefore(goDate.plusWeeks(1))) {
            comeDate = goDate.plusWeeks(1)
            binding.chooseComeDateText.text = comeDate.toString()
          }
          binding.chooseGoDateText.text = goDate.toString()
          Log.d("flightLog", "바꾼 후 goDate : $goDate")

        },
        goDate.year,
        goDate.monthValue - 1,
        goDate.dayOfMonth
      )

//      선택 가능한 범위 지정
      datePickerDialog.datePicker.minDate = System.currentTimeMillis()
      calendar.add(Calendar.MONTH, 6)
      datePickerDialog.datePicker.maxDate = calendar.timeInMillis

      datePickerDialog.show()
    }
  }

  //  오는 날 텍스트(chooseComeDateText, chooseComeDateArrow) 관련 함수
  fun chooseComeDate() {
    binding.chooseComeDateText.text = comeDate.toString()
    Log.d("flightLog", "바꾸기 전 comeDate : $comeDate")

//    누르면 캘린더 뷰 나옴
    binding.chooseComeDateText.setOnClickListener {
      val calendar = Calendar.getInstance()
      calendar.timeInMillis = System.currentTimeMillis()

      val datePickerDialog = DatePickerDialog(
        this,
        { _, year, month, dayOfMonth ->
          val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)

//          오는 날이 가는 날 전인지 체크
          if (selectedDate.isBefore(goDate)) {
            showAlert("오는 날은 가는 날 이후여야 합니다!")
          } else {
            comeDate = selectedDate
            binding.chooseComeDateText.text = comeDate.toString()
            Log.d("flightLog", "바꾼 후 comeDate : $comeDate")

          }
        },

        comeDate.year,
        // LocalDate의 monthValue는 1부터 시작하므로 -1 필요
        comeDate.monthValue - 1,
        comeDate.dayOfMonth
      )

//      선택 가능 범위 지정
      val minDateMillis = goDate.atStartOfDay().toEpochSecond(java.time.ZoneOffset.UTC) * 1000
      val maxDateMillis =
        goDate.plusMonths(6).atStartOfDay().toEpochSecond(java.time.ZoneOffset.UTC) * 1000

      datePickerDialog.datePicker.minDate = minDateMillis
      datePickerDialog.datePicker.maxDate = maxDateMillis

      datePickerDialog.show()
    }
  }

  //  알람 창 띄우는 함수
  private fun showAlert(message: String) {
    AlertDialog.Builder(this)
      .setTitle("Flight Log")
      .setMessage(message)
      .setPositiveButton("확인") { dialog, _ -> dialog.dismiss() }
      .show()
  }

  //  인원 선택 창으로 감
  fun chooseSelectPeople() {
    binding.choosePeopleText.setOnClickListener {
      val dialog = SelectPeopleDialogFragment()
      dialog.setOnPassengerSelectedListener(this)
      dialog.show(supportFragmentManager, "SelectPeopleDialog")
    }
  }

  //  총 인원 수 수정해주는 함수
  override fun onPassengerSelected(result: String) {
    binding.choosePeopleText.text = result
    Log.d("flightLog", "result : $result")
//    n 명 형식의 데이터를 공백을 기준으로 자르고, 그 0번째 값을 가져와서 숫자로 만든다
    selectedPeople = result.split(" ")[0].toInt()
    Log.d("flightLog", "selectedPeople = $selectedPeople")
  }

  //  출발지 설정
  fun chooseDeparture() {
    binding.departureText.setOnClickListener {
      val dialog = ChooseDepartureFragment()
      dialog.show(supportFragmentManager, "ChooseDepartureFragment")
    }
  }

  //  도착지 설정
  fun chooseArrive() {
    binding.arriveText.setOnClickListener {
      if (selectedDeparture == "") {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("출발지를 먼저 선택해주세요")
        builder.setPositiveButton("확인", null)
        builder.create()
        builder.show()
      } else {
        val dialog = ChooseArriveFragment()
        dialog.setSelectedDeparture(selectedDeparture)
        dialog.show(supportFragmentManager, "ChooseArriveFragment")
      }
    }
  }

  //  ChooseDepartureFragment 에서 가져온 출발지
//  MainActivity 의 출발지에 텍스트로 넣기
  override fun onDepartureSelected(departure: String) {
    binding.departureText.text = departure
    selectedDeparture = departure
    Log.d("flightLog", "출발지 : $departure")
  }

  //  ChooseArriveFragment 에서 가져온 출발지
//  MainActivity 의 도착지에 텍스트로 넣기
  override fun onArriveSelected(arrive: String) {
    binding.arriveText.text = arrive
    selectedArrive = arrive
    Log.d("flightLog", "도착지 : $arrive")
  }

  //  출발지와 도착지 바꾸기
  fun changeDestinationArrive() {
    binding.changeDestinationArriveArrow.setOnClickListener {
      tempLocation = selectedDeparture
      selectedDeparture = selectedArrive
      selectedArrive = tempLocation

      binding.departureText.text = selectedDeparture
      binding.arriveText.text = selectedArrive
    }
  }

  //  조회하기
  fun searchFlight() {
//    지금까지 선택한 출발지, 도착지, 출발일, 도착일이 GoAirplaneActivity 로 넘어감
    binding.flightSearch.setOnClickListener {
//      출발지와 도착지를 둘 다 선택하지 않은 경우
      if (selectedDeparture == "" && selectedArrive == "") {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("출발지와 도착지를 선택해주세요")
        builder.setPositiveButton("확인", null)
        builder.create()
        builder.show()
//        출발지 혹은 도착지를 선택하지 않은 경우
      } else if (selectedDeparture == "" || selectedArrive == "") {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("출발지 혹은 도착지를 선택해주세요")
        builder.setPositiveButton("확인", null)
        builder.create()
        builder.show()
      } else {
        val intent = Intent(this, GoAirplaneActivity::class.java)
        intent.putExtra("출발지", selectedDeparture)
        intent.putExtra("도착지", selectedArrive)
        intent.putExtra("출발일", goDate.toString())
        intent.putExtra("도착일", comeDate.toString())
        intent.putExtra("인원수", selectedPeople)
        startActivity(intent)
      }
    }
  }
}