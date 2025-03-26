package bitc.fullstack.FlightLog.flightchoose

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.FlightLog.R
import bitc.fullstack.FlightLog.databinding.ActivityComeAirplaneChooseSeatBinding
import bitc.fullstack.FlightLog.flightchoose.GoAirplaneChooseSeatActivity
import java.text.NumberFormat
import java.util.Locale

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

//오는 비행기 좌석 총 경비
private var comeAirplaneTotalPrice = 0

//왕복 비행기 값
private var totalPrice = 0

//한국 통화 형식으로 환산
private var formattedTotalPrice = ""

class ComeAirplaneChooseSeatActivity : AppCompatActivity() {
  private val binding: ActivityComeAirplaneChooseSeatBinding by lazy {
    ActivityComeAirplaneChooseSeatBinding.inflate(layoutInflater)
  }

  //  내가 고른 좌석의 텍스트값 저장
  private val selectedSeatNames = mutableListOf<String>()

  @SuppressLint("SetTextI18n")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(binding.root)

    ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }

    //    내가 ComeAirplaneActivity 에서 설정한 값들 가져오는거
    getExtra()

    //    일등석
    setUpFirstSeatSelection()

//    비즈니스석
    setUpBusinessSeatSelection()

//    일반석
    setUpRegularSeatSelection()

    binding.textComeSelectedPeople.text = "총 $selectedPeople 명"

  }

  //  ComeAirplaneActivity 에서 가져온 값
  fun getExtra() {
    //각 변수에 intent 에서 넘어온 값 대입
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

  //  일등석
  @SuppressLint("SetTextI18n")
  fun setUpFirstSeatSelection() {
    val firstSeatButtons = listOf(
      binding.comeSeatA1,
      binding.comeSeatA2,
      binding.comeSeatA3,
      binding.comeSeatA4,
      binding.comeSeatB1,
      binding.comeSeatB2,
      binding.comeSeatB3,
      binding.comeSeatB4,
      binding.comeSeatC1,
      binding.comeSeatC2,
      binding.comeSeatC3,
      binding.comeSeatC4
    )

//    각 버튼의 ID를 키로 사용해서 선택 상태를 저장하는 맵
    val seatSelectedState = mutableMapOf<Int, Boolean>()

    firstSeatButtons.forEach { button ->
//      각 버튼의 id 에 해당하는 기본 값은 false
      seatSelectedState[button.id] = false

      button.setOnClickListener {
//        버튼이 현재 선택된 상태(true) 인지 확인
        val isSelected = seatSelectedState[button.id] == true
//        내가 선택한 좌석의 text 값을 seatName 에 저장함
        val seatName = button.text.toString()

//        버튼이 선택되어있지 않다면
        if (!isSelected) {
//          선택된 좌석 수가 선택 가능한 인원 수보다 작다면
          if (selectedSeat < selectedPeople) {
//            버튼의 배경은 선택된 좌석으로
            button.background = ContextCompat.getDrawable(this, R.drawable.selected_first_seat)
//            상태는 true 로
            seatSelectedState[button.id] = true
//            선택된 좌석 수 증가
            selectedSeat++
//            n 명 좌석 선택 의 n 을 내가 고른 좌석 수로 설정함
            binding.textComeSelectedSeat.text = "$selectedSeat 명 좌석 선택"

//            내가 선택한 좌석의 이름을 selectedSeatNames 라는 배열에 추가함
            selectedSeatNames.add(seatName)

//            총 가격 = 지금까지의 가격 + ((거리 * 일등석 가격(1500))을 정수로 환산한 값)
            comeAirplaneTotalPrice = (comeAirplaneTotalPrice + (distance * firstSeatPrice).toInt())
            Log.d("flightLog", "comeAirplaneTotalPrice : $comeAirplaneTotalPrice")

//            가는 비행기 + 오는 비행기 가격
            totalPrice = goAirplaneTotalPrice + comeAirplaneTotalPrice
            Log.d("flightLog", "totalPrice = $totalPrice")
          } else {
//            선택 가능한 좌석 수 초과 시 안내 메세지 표시
            showSeatLimitDialog()
          }
        } else {
//          이미 선택된 좌석을 다시 클릭하면 선택 해제
//          버튼의 배경을 일반 좌석으로
          button.background = ContextCompat.getDrawable(this, R.drawable.first_seat)
//          해당 버튼을 선택 해제(false) 상태로
          seatSelectedState[button.id] = false
//          선택된 좌석 수 감소
          selectedSeat--
          //n 명 좌석 선택 의 n 을 내가 고른 좌석 수로 설정함
          binding.textComeSelectedSeat.text = "$selectedSeat 명 좌석 선택"

          //내가 선택한 좌석의 이름을 selectedSeatNames 라는 배열에서 제거함
          selectedSeatNames.remove(seatName)

//            총 가격 = 지금까지의 가격 - ((거리 * 일등석 가격(1500))을 정수로 환산한 값)
          comeAirplaneTotalPrice = (comeAirplaneTotalPrice - (distance * firstSeatPrice).toInt())
          Log.d("flightLog", "comeAirplaneTotalPrice : $comeAirplaneTotalPrice")

//          가는 비행기 + 오는 비행기 가격
          totalPrice = goAirplaneTotalPrice + comeAirplaneTotalPrice
          Log.d("flightLog", "totalPrice = $totalPrice")
        }

//        선택 좌석에 내가 고른 좌석들의 목록을 배열으로 출력
//        그 배열을 출력하되, 배열 안의 요소 각각의 값은 ', ' 형태로 나눔
        binding.textComeSelectedSeatList.text = "선택 좌석 : ${selectedSeatNames.joinToString(", ")}"
        Log.d("flightLog", "selectedSeat : $selectedSeat")
      }
    }
  }

  //  비즈니스석
  @SuppressLint("SetTextI18n")
  fun setUpBusinessSeatSelection() {
    val businessSeatButtons = listOf(
      binding.comeSeatD1,
      binding.comeSeatD2,
      binding.comeSeatD3,
      binding.comeSeatD4,
      binding.comeSeatD5,
      binding.comeSeatD6,

      binding.comeSeatE1,
      binding.comeSeatE2,
      binding.comeSeatE3,
      binding.comeSeatE4,
      binding.comeSeatE5,
      binding.comeSeatE6,

      binding.comeSeatF1,
      binding.comeSeatF2,
      binding.comeSeatF3,
      binding.comeSeatF4,
      binding.comeSeatF5,
      binding.comeSeatF6,
    )

//    각 버튼의 ID를 키로 사용해서 선택 상태를 저장하는 맵
    val seatSelectedState = mutableMapOf<Int, Boolean>()

    businessSeatButtons.forEach { button ->
//      각 버튼의 id 에 해당하는 기본 값은 false
      seatSelectedState[button.id] = false

      button.setOnClickListener {
//        버튼이 현재 선택된 상태(true) 인지 확인
        val isSelected = seatSelectedState[button.id] == true
//        내가 선택한 좌석의 text 값을 seatName 에 저장함
        val seatName = button.text.toString()

//        버튼이 선택되어있지 않다면
        if (!isSelected) {
//          선택된 좌석 수가 선택 가능한 인원 수보다 작다면
          if (selectedSeat < selectedPeople) {
//            버튼의 배경은 선택된 좌석으로
            button.background = ContextCompat.getDrawable(this, R.drawable.selected_business_seat)
//            상태는 true 로
            seatSelectedState[button.id] = true
//            선택된 좌석 수 증가
            selectedSeat++
            //n 명 좌석 선택 의 n 을 내가 고른 좌석 수로 설정함
            binding.textComeSelectedSeat.text = "$selectedSeat 명 좌석 선택"

            //내가 선택한 좌석의 이름을 selectedSeatNames 라는 배열에 추가함
            selectedSeatNames.add(seatName)

            comeAirplaneTotalPrice =
              (comeAirplaneTotalPrice + (distance * businessSeatPrice).toInt())
            Log.d("flightLog", "comeAirplaneTotalPrice : $comeAirplaneTotalPrice")

//          가는 비행기 + 오는 비행기 가격
            totalPrice = goAirplaneTotalPrice + comeAirplaneTotalPrice
            Log.d("flightLog", "totalPrice = $totalPrice")
          } else {
//            선택 가능한 좌석 수 초과 시 안내 메세지 표시
            showSeatLimitDialog()
          }
        } else {
//          이미 선택된 좌석을 다시 클릭하면 선택 해제
//          버튼의 배경을 일반 좌석으로
          button.background = ContextCompat.getDrawable(this, R.drawable.business_seat)
//          해당 버튼을 선택 해제(false) 상태로
          seatSelectedState[button.id] = false
//          선택된 좌석 수 감소
          selectedSeat--
          //n 명 좌석 선택 의 n 을 내가 고른 좌석 수로 설정함
          binding.textComeSelectedSeat.text = "$selectedSeat 명 좌석 선택"

          //내가 선택한 좌석의 이름을 selectedSeatNames 라는 배열에서 제거함
          selectedSeatNames.remove(seatName)

          comeAirplaneTotalPrice = (comeAirplaneTotalPrice - (distance * businessSeatPrice).toInt())
          Log.d("flightLog", "comeAirplaneTotalPrice : $comeAirplaneTotalPrice")

//          가는 비행기 + 오는 비행기 가격
          totalPrice = goAirplaneTotalPrice + comeAirplaneTotalPrice
          Log.d("flightLog", "totalPrice = $totalPrice")
        }
        //선택 좌석에 내가 고른 좌석들의 목록을 배열으로 출력
//        그 배열을 출력하되, 배열 안의 요소 각각의 값은 ', ' 형태로 나눔
        binding.textComeSelectedSeatList.text = "선택 좌석 : ${selectedSeatNames.joinToString(", ")}"
        Log.d("flightLog", "selectedSeat : $selectedSeat")
      }
    }
  }

  //  일반석
  @SuppressLint("SetTextI18n")
  fun setUpRegularSeatSelection() {
    val regularSeatButtons = listOf(
      binding.comeSeatG1,
      binding.comeSeatG2,
      binding.comeSeatG3,
      binding.comeSeatG4,

      binding.comeSeatH1,
      binding.comeSeatH2,
      binding.comeSeatH3,
      binding.comeSeatH4,

      binding.comeSeatI1,
      binding.comeSeatI2,
      binding.comeSeatI3,
      binding.comeSeatI4,
    )

//    각 버튼의 ID를 키로 사용해서 선택 상태를 저장하는 맵
    val seatSelectedState = mutableMapOf<Int, Boolean>()

    regularSeatButtons.forEach { button ->
//      각 버튼의 id 에 해당하는 기본 값은 false
      seatSelectedState[button.id] = false

      button.setOnClickListener {
//        버튼이 현재 선택된 상태(true) 인지 확인
        val isSelected = seatSelectedState[button.id] == true
//        내가 선택한 좌석의 text 값을 seatName 에 저장함
        val seatName = button.text.toString()

//        버튼이 선택되어있지 않다면
        if (!isSelected) {
//          선택된 좌석 수가 선택 가능한 인원 수보다 작다면
          if (selectedSeat < selectedPeople) {
//            버튼의 배경은 선택된 좌석으로
            button.background = ContextCompat.getDrawable(this, R.drawable.selected_regular_seat)
//            상태는 true 로
            seatSelectedState[button.id] = true
//            선택된 좌석 수 증가
            selectedSeat++
            //n 명 좌석 선택 의 n 을 내가 고른 좌석 수로 설정함
            binding.textComeSelectedSeat.text = "$selectedSeat 명 좌석 선택"

            //내가 선택한 좌석의 이름을 selectedSeatNames 라는 배열에 추가함
            selectedSeatNames.add(seatName)

            comeAirplaneTotalPrice =
              (comeAirplaneTotalPrice + (distance * regularSeatPrice).toInt())
            Log.d("flightLog", "comeAirplaneTotalPrice : $comeAirplaneTotalPrice")

//          가는 비행기 + 오는 비행기 가격
            totalPrice = goAirplaneTotalPrice + comeAirplaneTotalPrice
            Log.d("flightLog", "totalPrice = $totalPrice")
          } else {
//            선택 가능한 좌석 수 초과 시 안내 메세지 표시
            showSeatLimitDialog()
          }
        } else {
//          이미 선택된 좌석을 다시 클릭하면 선택 해제
//          버튼의 배경을 일반 좌석으로
          button.background = ContextCompat.getDrawable(this, R.drawable.blue_button)
//          해당 버튼을 선택 해제(false) 상태로
          seatSelectedState[button.id] = false
//          선택된 좌석 수 감소
          selectedSeat--
          //n 명 좌석 선택 의 n 을 내가 고른 좌석 수로 설정함
          binding.textComeSelectedSeat.text = "$selectedSeat 명 좌석 선택"

          //내가 선택한 좌석의 이름을 selectedSeatNames 라는 배열에서 제거함
          selectedSeatNames.remove(seatName)

          comeAirplaneTotalPrice = (comeAirplaneTotalPrice - (distance * regularSeatPrice).toInt())
          Log.d("flightLog", "comeAirplaneTotalPrice : $comeAirplaneTotalPrice")

//          가는 비행기 + 오는 비행기 가격
          totalPrice = goAirplaneTotalPrice + comeAirplaneTotalPrice
          Log.d("flightLog", "totalPrice = $totalPrice")
        }
        //선택 좌석에 내가 고른 좌석들의 목록을 배열으로 출력
//        그 배열을 출력하되, 배열 안의 요소 각각의 값은 ', ' 형태로 나눔
        binding.textComeSelectedSeatList.text = "선택 좌석 : ${selectedSeatNames.joinToString(", ")}"
        Log.d("flightLog", "selectedSeat : $selectedSeat")
      }
    }
  }

  //  인원 수를 초과하면 실행할 함수
  private fun showSeatLimitDialog() {
    AlertDialog.Builder(this@ComeAirplaneChooseSeatActivity)
      .setTitle("좌석 선택 초과")
      .setMessage("선택한 좌석 수가 인원 수보다 많습니다")
      .setPositiveButton("확인", null)
      .show()
  }
}