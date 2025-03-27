package bitc.fullstack.FlightLog.flightchoose

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.FlightLog.R
import bitc.fullstack.FlightLog.databinding.ActivityPassengerBinding
import androidx.core.view.isGone
import bitc.fullstack.FlightLog.appserver.AppServerClass
import bitc.fullstack.FlightLog.dto.flightUserDTO
import bitc.fullstack.FlightLog.sidebar.TicketHolderActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//  출발도시, 도착도시, 가는날
private var selectedDeparture: String = ""
private var selectedArrive: String = ""
private var goDate: String = ""
private var comeDate: String = ""
private var selectedPeople: Int = 0
private var selectedSeat: Int = 0
private var distance: Double = 0.0
private var goAirplaneFlightId = 0
private var selectedStartSeatNames: String = ""
private var selectedStartSeatNameList = listOf<String>()

//가는 비행기 좌석 총 경비
private var goAirplaneTotalPrice = 0

//한국 통화 형식으로 환산
private var formattedGoAirplane = ""

private var userId = "test1234"

private var passportNumber = ""
private var luggageWeight = ""
private var userFirstName = ""
private var userLastName = ""


class PassengerActivity : AppCompatActivity() {
  private val binding: ActivityPassengerBinding by lazy {
    ActivityPassengerBinding.inflate(layoutInflater)
  }

  var buttonClick = 1

  //  내가 고른 좌석의 텍스트값 저장
//  private val selectedSeatNames = mutableListOf<String>()

  @SuppressLint("InflateParams")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(binding.root)
    ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }

    //    내가 저번 view 에서 받아온거 확인용
    getExtra()

//    동승자가 2명보다 많은지 체크해서 동승자 추가하기 버튼을 넣을지 말지
    isSelectedPeopleBiggerThen2()

//    동승자 수 만큼 동승자 추가하기 버튼 클릭 가능하게
    plusPeopleInfoButton()

    //현재 로그인한 유저의 이름 가져오기
    getUserName()

    binding.reserveGoAirplaneBtn.setOnClickListener {
      //    입력받은 여권 번호, 수하물 무게 출력
      userFirstName = binding.firstNameUser.text.toString()
      userLastName = binding.lastNameUser.text.toString()
      passportNumber = binding.passportNumberUser.text.toString()
      luggageWeight = binding.luggageWeightUser.text.toString() + "kg"
      Log.d("flightLog", "userFirstName : $userFirstName")
      Log.d("flightLog", "userLastName : $userLastName")
      Log.d("flightLog", "passportNumber : $passportNumber")
      Log.d("flightLog", "luggageWeight : $luggageWeight")

//      flight_reserve_member 에 저장하기
      val userName = AppServerClass.instance
      val call = userName.goFlightAlone(
        passportNumber,
        userId,
        userFirstName,
        userLastName,
        selectedStartSeatNames,
        luggageWeight
      )
      retrofitResponseAlone(call)

      AlertDialog.Builder(this)
        .setTitle("가는 티켓 예매 완료")
        .setMessage("오는 티켓도 예매 하시겠습니까?")
        .setPositiveButton("예", object : DialogInterface.OnClickListener {
          override fun onClick(dialog: DialogInterface?, which: Int) {

            val intent = Intent(this@PassengerActivity, ComeAirplaneActivity::class.java)
            intent.putExtra("가는 비행기 아이디", goAirplaneFlightId)
            intent.putExtra("출발지", selectedDeparture)
            intent.putExtra("도착지", selectedArrive)
            intent.putExtra("출발일", goDate.toString())
            intent.putExtra("도착일", comeDate.toString())
            intent.putExtra("인원수", selectedPeople)
            intent.putExtra("거리", distance)
            intent.putExtra("가는 비행기 총 비용", goAirplaneTotalPrice)
            intent.putExtra("가는 비행기 선택 좌석", selectedStartSeatNames)
            startActivity(intent)
          }
        })
        .setNegativeButton("아니요", object : DialogInterface.OnClickListener {
          override fun onClick(dialog: DialogInterface?, which: Int) {
            val intent = Intent(this@PassengerActivity, TicketHolderActivity::class.java)
            startActivity(intent)
          }
        })
        .create()
        .show()
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    userFirstName = ""
    userLastName = ""
    passportNumber = ""
    luggageWeight = ""
  }

  fun getExtra() {
    //    각 변수에 intent 에서 넘어온 값 대입
    goAirplaneFlightId = intent.getIntExtra("가는 비행기 아이디", 0)
    selectedDeparture = intent.getStringExtra("출발지").toString()
    selectedArrive = intent.getStringExtra("도착지").toString()
    goDate = intent.getStringExtra("출발일").toString()
    comeDate = intent.getStringExtra("도착일").toString()
    selectedPeople = intent.getIntExtra("인원수", 1)
    distance = intent.getDoubleExtra("거리", 0.0)
    selectedStartSeatNames = intent.getStringExtra("가는 비행기 선택 좌석").toString()
    selectedStartSeatNameList = selectedStartSeatNames.split(",")

//    확인용
    Log.d("flightLog", "------------PassengerActivity-------------")
    Log.d("flightLog", "goAirplaneFlightId = $goAirplaneFlightId")
    Log.d("flightLog", "selectedDeparture = $selectedDeparture")
    Log.d("flightLog", "selectedArrive = $selectedArrive")
    Log.d("flightLog", "goDate = $goDate")
    Log.d("flightLog", "comeDate = $comeDate")
    Log.d("flightLog", "selectedPeople = $selectedPeople")
    Log.d("flightLog", "distance = $distance")
    Log.d("flightLog", "selectedStartSeatNames = $selectedStartSeatNames")
    Log.d("flightLog", "selectedStartSeatNameList = ${selectedStartSeatNameList.toString()}")
  }

  //    인원 수가 2명 이상이면 인원 추가 버튼 보이게
  fun isSelectedPeopleBiggerThen2() {
    if (selectedPeople >= 2) {
      binding.plusPeopleInfoBtn.visibility = VISIBLE
    } else {
      binding.plusPeopleInfoBtn.visibility = GONE
    }
  }

  //    동승자 추가하기 버튼 누르면
  fun plusPeopleInfoButton() {
    binding.plusPeopleInfoBtn.setOnClickListener {
//      버튼을 누른 횟수가 동승자 사람수보다 적다면 버튼 누른 횟수를 1 늘림
      if (buttonClick < selectedPeople) {
        buttonClick++
        Log.d("flightLog", "plusPeopleInfo 누른 횟수 : $buttonClick")

//        새로운 동승객 입력 레이아웃 추가
        val passengerView = layoutInflater.inflate(R.layout.passenger_info, null)
        binding.moreInfoLayout.addView(passengerView)

//        버튼이 항상 맨 밑으로 가게끔 뷰에서 삭제했다가 다시 넣어줌
        binding.moreInfoLayout.removeView(binding.plusPeopleInfoBtn)
        binding.moreInfoLayout.removeView(binding.reserveGoAirplaneBtn)
        binding.moreInfoLayout.addView(binding.plusPeopleInfoBtn)
        binding.moreInfoLayout.addView(binding.reserveGoAirplaneBtn)

//        모든 동승객을 추가하면 버튼 사라짐
        if (buttonClick == selectedPeople) {
          binding.plusPeopleInfoBtn.visibility = GONE
        }

      }
    }
  }

  //  로그인한 유저 성, 이름 가져오기
  fun getUserName() {
    val userName = AppServerClass.instance
    val call = userName.getUserName(
      userId,
    )
    retrofitResponse(call)
  }

  //  Retrofit 통신 응답 List<List<flightUserDTO> : 예매 용
  private fun retrofitResponse(call: Call<List<flightUserDTO>>) {
    call.enqueue(object : Callback<List<flightUserDTO>> {
      @SuppressLint("NotifyDataSetChanged")
      override fun onResponse(p0: Call<List<flightUserDTO>>, res: Response<List<flightUserDTO>>) {
        if (res.isSuccessful) {
          Log.d("flightLog", "성공")
          val result = res.body()

          if (!result.isNullOrEmpty()) {
            val user = result[0]
            val firstName = user.flightUserFirstname
            val lastName = user.flightUserLastname
            Log.d("flightLog", "firstName : $firstName")
            Log.d("flightLog", "lastName : $lastName")

            binding.lastNameUser.setText(lastName)
            binding.firstNameUser.setText(firstName)
          }
        } else {
          Log.d("flightLog", "실패. 응답 코드 : ${res.code()}")
        }
      }

      override fun onFailure(p0: Call<List<flightUserDTO>>, t: Throwable) {
        Log.d("flightLog", "message : $t.message")
      }
    })
  }

  //  Retrofit 통신 응답 List<Void> : 예매 용
  private fun retrofitResponseAlone(call: Call<Void>) {
    call.enqueue(object : Callback<Void> {
      @SuppressLint("NotifyDataSetChanged")
      override fun onResponse(p0: Call<Void>, res: Response<Void>) {
        if (res.isSuccessful) {
          Log.d("flightLog", "성공")
        } else {
          Log.d("flightLog", "실패. 응답 코드 : ${res.code()}")
        }
      }

      override fun onFailure(p0: Call<Void>, t: Throwable) {
        Log.d("flightLog", "message : $t.message")
      }
    })
  }
}

