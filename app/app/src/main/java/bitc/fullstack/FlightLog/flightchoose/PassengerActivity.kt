package bitc.fullstack.FlightLog.flightchoose

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.FlightLog.R
import bitc.fullstack.FlightLog.appserver.AppServerClass
import bitc.fullstack.FlightLog.databinding.ActivityPassengerBinding
import bitc.fullstack.FlightLog.dto.flightUserDTO
import bitc.fullstack.FlightLog.flightmain.MainActivity
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
private var distance: Double = 0.0
private var goAirplaneFlightId = 0
private var comeAirplaneFlightId = 0
private var roundTripChecked = false

//가는 비행기 예매 좌석
private var selectedStartSeatNames: String = ""
private var selectedStartSeatNameList = listOf<String>()

//오는 비행기 예매 좌석
private var selectedArriveSeatNames: String = ""
private var selectedArriveSeatNameList = listOf<String>()

//임시 예약 번호(편도)
private var flightReno: String = ""

//임시 예약 번호(왕복)
private var roundFlightReno: String = ""

//왕복 비행기를 검색하고 싶었는데 돌아오는 비행기가 없어서
//어쩔 수 없이 편도로 예매해야 할 때 사용하는 예약번호
private var noComeAirplaneReno: String = ""

//가는 비행기 좌석 총 경비
private var goAirplaneTotalPrice = 0

//한국 통화 형식으로 환산
private var formattedGoAirplane = ""

private var userId = "test1234"

//여권번호, 수하물, 이름, 성 등 저장
private var passportNumber = ""
private var luggageWeight = ""
private var userFirstName = ""
private var userLastName = ""

class PassengerActivity : AppCompatActivity() {
  private val binding: ActivityPassengerBinding by lazy {
    ActivityPassengerBinding.inflate(layoutInflater)
  }

  //  동승객 정보 추가 버튼 클릭횟수 세는 변수
  var buttonClick = 1

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

    //    저장버튼 누르면 실제 db 저장
    saveButton()
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
    roundTripChecked = intent.getBooleanExtra("왕복 선택 여부", false)
    goAirplaneFlightId = intent.getIntExtra("가는 비행기 아이디", 0)
    comeAirplaneFlightId = intent.getIntExtra("오는 비행기 아이디", 0)
    selectedDeparture = intent.getStringExtra("출발지").toString()
    selectedArrive = intent.getStringExtra("도착지").toString()
    goDate = intent.getStringExtra("출발일").toString()
    comeDate = intent.getStringExtra("도착일").toString()
    selectedPeople = intent.getIntExtra("인원수", 1)
    distance = intent.getDoubleExtra("거리", 0.0)

//    가는 비행기 좌석
    selectedStartSeatNames = intent.getStringExtra("가는 비행기 선택 좌석").toString()
    selectedStartSeatNameList = selectedStartSeatNames.split(",")

//    오는 비행기 좌석
    selectedArriveSeatNames = intent.getStringExtra("오는 비행기 선택 좌석").toString()
    selectedArriveSeatNameList = selectedArriveSeatNames.split(",")

//    예약 번호
    flightReno = intent.getStringExtra("예약 번호").toString()
    roundFlightReno = intent.getStringExtra("왕복 예약 번호").toString()
    noComeAirplaneReno = intent.getStringExtra("왕복 비행기 없을 때").toString()

//    확인용
    Log.d("flightLog", "------------PassengerActivity-------------")
    Log.d("flightLog", "roundTripChecked : $roundTripChecked")
    Log.d("flightLog", "goAirplaneFlightId = $goAirplaneFlightId")
    Log.d("flightLog", "comeAirplaneFlightId = $comeAirplaneFlightId")
    Log.d("flightLog", "selectedDeparture = $selectedDeparture")
    Log.d("flightLog", "selectedArrive = $selectedArrive")
    Log.d("flightLog", "goDate = $goDate")
    Log.d("flightLog", "comeDate = $comeDate")
    Log.d("flightLog", "selectedPeople = $selectedPeople")
    Log.d("flightLog", "distance = $distance")
    Log.d("flightLog", "selectedStartSeatNames = $selectedStartSeatNames")
    Log.d("flightLog", "selectedStartSeatNameList = $selectedStartSeatNameList")
    Log.d("flightLog", "selectedArriveSeatNames = $selectedArriveSeatNames")
    Log.d("flightLog", "selectedArriveSeatNameList = $selectedArriveSeatNameList")
    Log.d("flightLog", "flightReno = $flightReno")
    Log.d("flightLog", "roundFlightReno = $roundFlightReno")
    Log.d("flightLog", "noComeAirplaneReno = $noComeAirplaneReno")
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
        val passengerView =
          layoutInflater.inflate(R.layout.passenger_info, binding.moreInfoLayout, false)
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

  //  저장 버튼 누르면 실제 db 저장
  fun saveButton() {
    binding.reserveGoAirplaneBtn.setOnClickListener {
//      탑승자 정보를 담기 위한 배열
      val firstNames = mutableListOf<String>()
      val lastNames = mutableListOf<String>()
      val passports = mutableListOf<String>()
      val luggageWeights = mutableListOf<String>()
      val startSeatPriceList = mutableListOf<Int>()
      val arriveSeatPriceList = mutableListOf<Int>()

//      배열에 텍스트 값을 넣음
      firstNames.add(binding.firstNameUser.text.toString())
      lastNames.add(binding.lastNameUser.text.toString())
      passports.add(binding.passportNumberUser.text.toString())
      luggageWeights.add(binding.luggageWeightUser.text.toString() + "kg")

//      가격 계산(편도)
      for (seat in selectedStartSeatNameList) {
        val seatFirstChar = seat.substring(0, 1)
        val startSeatPrice = when (seatFirstChar) {
          "A", "B", "C" -> (distance * 1500).toInt()
          "D", "E", "F" -> (distance * 1000).toInt()
          "G", "H", "I" -> (distance * 500).toInt()
          else -> 0
        }
        startSeatPriceList.add(startSeatPrice)
      }

//      가격 계산(왕복)
      for (seat in selectedArriveSeatNameList) {
        val seatFirstChar = seat.substring(0, 1)
        val arriveSeatPrice = when (seatFirstChar) {
          "A", "B", "C" -> (distance * 1500).toInt()
          "D", "E", "F" -> (distance * 1000).toInt()
          "G", "H", "I" -> (distance * 500).toInt()
          else -> 0
        }
        arriveSeatPriceList.add(arriveSeatPrice)
      }

//      동승자 정보 추가 (passenger_more_info_layout 의 자식 수 만큼)
      for (i in 0 until buttonClick - 1) {
        val passengerView = findViewById<LinearLayout>(R.id.passenger_more_info_layout)
        val firstName =
          passengerView.findViewById<EditText>(R.id.first_name_passenger).text.toString()
        val lastName =
          passengerView.findViewById<EditText>(R.id.last_name_passenger).text.toString()
        val passport =
          passengerView.findViewById<EditText>(R.id.passport_number_passenger).text.toString()
        val luggage =
          passengerView.findViewById<EditText>(R.id.luggage_weight_passenger).text.toString() + "kg"

//        더 넣을 거 있으면 더 넣기
        firstNames.add(firstName)
        lastNames.add(lastName)
        passports.add(passport)
        luggageWeights.add(luggage)
      }

      Log.d("flightLog", "승객 수: ${firstNames.size}, 좌석 수: ${selectedStartSeatNameList.size}")

      if (firstNames.size != selectedStartSeatNameList.size) {
        AlertDialog.Builder(this@PassengerActivity)
          .setMessage("승객 수 만큼의 정보를 작성해주세요")
          .setPositiveButton("확인", null)
          .create()
          .show()
        return@setOnClickListener
      } else {
        val api = AppServerClass.instance
//      편도 여행이면
        if (roundTripChecked == false && noComeAirplaneReno == "") {
//          firstNames 의 길이만큼 n 번째 값을 reserveGoAirplaneMember 에 계속해서 넣어줌
          for (i in firstNames.indices) {
            val call = api.reserveGoAirplaneMember(
              passports[i],
              flightReno,
              userId,
              firstNames[i],
              lastNames[i],
              selectedStartSeatNameList[i],
              startSeatPriceList[i],
              luggageWeights[i]
            )
            retrofitResponseAlone(call)
          }

          AlertDialog.Builder(this)
            .setTitle("편도 여행 티켓 예매 완료")
            .setMessage("티켓 홀더로 이동하시겠습니까?")
            .setPositiveButton("예", object : DialogInterface.OnClickListener {
              override fun onClick(dialog: DialogInterface?, which: Int) {
                val intent = Intent(this@PassengerActivity, TicketHolderActivity::class.java)
                startActivity(intent)
              }
            })
            .setNegativeButton("아니요", object : DialogInterface.OnClickListener {
              override fun onClick(dialog: DialogInterface?, which: Int) {
                val intent = Intent(this@PassengerActivity, MainActivity::class.java)
                startActivity(intent)
              }
            })
            .create()
            .show()
        } else if (roundTripChecked == false && noComeAirplaneReno != null){
          for (i in firstNames.indices) {
            val call = api.reserveGoAirplaneMember(
              passports[i],
              noComeAirplaneReno,
              userId,
              firstNames[i],
              lastNames[i],
              selectedStartSeatNameList[i],
              startSeatPriceList[i],
              luggageWeights[i]
            )
            retrofitResponseAlone(call)
          }

          AlertDialog.Builder(this)
            .setTitle("편도 여행 티켓 예매 완료")
            .setMessage("티켓 홀더로 이동하시겠습니까?")
            .setPositiveButton("예", object : DialogInterface.OnClickListener {
              override fun onClick(dialog: DialogInterface?, which: Int) {
                val intent = Intent(this@PassengerActivity, TicketHolderActivity::class.java)
                startActivity(intent)
              }
            })
            .setNegativeButton("아니요", object : DialogInterface.OnClickListener {
              override fun onClick(dialog: DialogInterface?, which: Int) {
                val intent = Intent(this@PassengerActivity, MainActivity::class.java)
                startActivity(intent)
              }
            })
            .create()
            .show()
        }
        else {
//        roundTripChecked 가 True(왕복 여행) 이면
          Log.d("flightLog", "-------------------------------")
          Log.d("flightLog", "selectedArriveSeatNames : $selectedArriveSeatNames")
          Log.d("flightLog", "-------------------------------")

          // firstNames 의 길이만큼 n 번째 값을 reserveRoundAirplaneMember 에 계속해서 넣어줌
          for (i in firstNames.indices) {
            val call = api.reserveRoundAirplaneMember(
              passports[i],
              roundFlightReno,
              userId,
              firstNames[i],
              lastNames[i],
              selectedStartSeatNameList[i],
              startSeatPriceList[i],
              luggageWeights[i],
              selectedArriveSeatNameList[i],
              arriveSeatPriceList[i]
            )
            retrofitResponseAlone(call)
          }

          AlertDialog.Builder(this)
            .setTitle("왕복 여행 티켓 예매 완료")
            .setMessage("티켓 홀더로 이동하시겠습니까?")
            .setPositiveButton("예", object : DialogInterface.OnClickListener {
              override fun onClick(dialog: DialogInterface?, which: Int) {
                val intent = Intent(this@PassengerActivity, TicketHolderActivity::class.java)
                startActivity(intent)
              }
            })
            .setNegativeButton("아니요", object : DialogInterface.OnClickListener {
              override fun onClick(dialog: DialogInterface?, which: Int) {
                val intent = Intent(this@PassengerActivity, MainActivity::class.java)
                startActivity(intent)
              }
            })
            .create()
            .show()
        }
      }
    }
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
          Log.d("flightLog", "${res.errorBody()}")
        }
      }

      override fun onFailure(p0: Call<Void>, t: Throwable) {
        Log.d("flightLog", "message : $t.message")
      }
    })
  }
}

