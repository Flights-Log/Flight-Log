package bitc.fullstack.FlightLog.flightchoose

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bitc.fullstack.FlightLog.R
import bitc.fullstack.FlightLog.appserver.AppServerClass
import bitc.fullstack.FlightLog.databinding.ActivityGoAirplaneBinding
import bitc.fullstack.FlightLog.databinding.ItemGoAirplaneBinding
import bitc.fullstack.FlightLog.dto.flightInfoDTO
import bitc.fullstack.FlightLog.flightmain.MainActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

//  출발도시, 도착도시, 가는날, 오는 날, 선택한 사람 수, 거리
private var selectedDeparture: String = ""
private var selectedArrive: String = ""
private var goDate: String = ""
private var comeDate: String = ""
private var selectedPeople: Int = 0
private var roundTripChecked: Boolean = false

class GoAirplaneActivity : AppCompatActivity() {
  private val binding: ActivityGoAirplaneBinding by lazy {
    ActivityGoAirplaneBinding.inflate(layoutInflater)
  }

  //  내가 검색한 항공기의 이름, 출발 시간, 도착 시간, 가격을 저장함
  private val goAirplaneInfoList = mutableListOf<flightInfoDTO>()

  //  어댑터
  private lateinit var adapter: MyAdapterGoAirplane

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(binding.root)
    setContentView(R.layout.activity_go_airplane)

    ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }

//    내가 MainActivity 에서 설정한 값들 가져오는거
    getExtra()

//    recyclerView 라는 변수에 binding(ActivityGoAirplane) 의 goAirplaneRecyclerView 를 가져옴
//    adapter 에 goAirplaneInfoList 를 받아서 myAdapterGoAirplane 클래스 실행
    adapter = MyAdapterGoAirplane(goAirplaneInfoList)

//    실행한 adapter 를 리사이클러뷰의 어댑터에 넣어줌
    binding.goAirplaneRecyclerView.adapter = adapter

//    goAirplaneRecyclerView 의 레이아웃은 LinearLayout 으로 함
    binding.goAirplaneRecyclerView.layoutManager = LinearLayoutManager(this)

//    goAirplaneRecyclerView 의 데코레이션(꾸미기) 속성은 vertical 으로
    binding.goAirplaneRecyclerView.addItemDecoration(
      DividerItemDecoration(
        this,
        LinearLayoutManager.VERTICAL
      )
    )

    //출발지 장소 리스트로 가져오기
    val api = AppServerClass.instance
    val call = api.searchGoAirplane(selectedDeparture, selectedArrive, goDate)
    retrofitResponse(call)
  }

  override fun onResume() {
    super.onResume()

//    새 출발일이 있다면 업데이트
    intent.getStringExtra("출발일")?.let {
      goDate = it
      binding.flightDepartureDate.text = it
      val api = AppServerClass.instance
      Log.d("flightLog", "goDate : $goDate")
      val call = api.searchGoAirplane(selectedDeparture, selectedArrive, goDate)
      retrofitResponse(call)
    }
  }

  //  내가 MainActivity 에서 intent 로 받아온 값 화면과 로그에 출력
//  그리고 위에 있는 startCity, selectedArrive, goDate 에 넣어줌
  fun getExtra() {
    Log.d("flightLog", "---------GoAirplaneActivity----------")
    binding.textStartCity.text = intent.getStringExtra("출발지")
    selectedDeparture = binding.textStartCity.text.toString()

    binding.textArrivalCity.text = intent.getStringExtra("도착지")
    selectedArrive = binding.textArrivalCity.text.toString()

    binding.flightDepartureDate.text = intent.getStringExtra("출발일")
    goDate = binding.flightDepartureDate.text.toString()

    Log.d("flightLog", "받은 도착일 : ${intent.getStringExtra("도착일")}")
    comeDate = intent.getStringExtra("도착일").toString()

    Log.d("flightLog", "인원수 : ${intent.getIntExtra("인원수", 1)}")
    selectedPeople = intent.getIntExtra("인원수", 1)

    Log.d("flightLog", "왕복 선택 여부 : ${intent.getBooleanExtra("왕복 선택 여부", false)}")
    roundTripChecked = intent.getBooleanExtra("왕복 선택 여부", false)
  }

  //  Retrofit 통신 응답 List<String>
  private fun retrofitResponse(call: Call<List<flightInfoDTO>>) {
    call.enqueue(object : Callback<List<flightInfoDTO>> {
      @SuppressLint("NotifyDataSetChanged")
      override fun onResponse(p0: Call<List<flightInfoDTO>>, res: Response<List<flightInfoDTO>>) {
        if (res.isSuccessful) {
          val result = res.body()

//          어댑터 내용이 변경될 때 전체 레이아웃이 무효화 되는 것 방지
//          아이템 크기가 변하지 않는다면 성능 최적화
//          binding.goAirplaneRecyclerView.setHasFixedSize(true)

//          조회된 결과가 없다면
          if (result.isNullOrEmpty()) {
//            조호된 결과가 없습니다 를 넣은 레이아웃이 보이게 하기
//            그리고 리사이클러뷰는 안보이게 하기
            binding.noGoAirplaneLayout.visibility = View.VISIBLE
            binding.goAirplaneRecyclerView.visibility = View.GONE

            val api = AppServerClass.instance
            val call = api.recommendStartDate(
              selectedDeparture,
              selectedArrive
            )
            retrofitResponseIn(call)
          } else {
//            조회된 결과가 잇다면 리사이클러뷰 출력
            binding.noGoAirplaneLayout.visibility = View.GONE
            binding.goAirplaneRecyclerView.visibility = View.VISIBLE

//            내가 찾아온 비행기의 목록인 goAirplaneInfoList 를 비우고
//            가져온 값인 result 를 goAirplaneInfoList 에 배열 형식으로 넣는다
//            그리고 adapter 의 notifyDataSetChanged() 를 실행시켜 리사이클러 뷰 내부 항목을 새로 보이게 한다
            goAirplaneInfoList.clear()
            goAirplaneInfoList.addAll(result)
            adapter.notifyDataSetChanged()
          }
        } else {
          Log.d("flightLog", "송신 실패")
        }
      }

      override fun onFailure(p0: Call<List<flightInfoDTO>>, t: Throwable) {
        Log.d("flightLog", "message : $t.message")
      }
    })
  }

  //  Retrofit 통신 응답 List<String>
  private fun retrofitResponseIn(call: Call<ResponseBody>) {
    call.enqueue(object : Callback<ResponseBody> {
      override fun onResponse(
        p0: Call<ResponseBody>,
        res: Response<ResponseBody>
      ) {
        if (res.isSuccessful) {

          val result = res.body()?.string()
          Log.d("flightLog", "result : $result")

//          원래는 data class 를 사용 하는데 귀찮아서 Scope Function 사용
//          매개변수화된 타입 (originalDatE) 의 값을 받아서 자기 자신을 반환 (result)
//          person.let 형식으로 작성 가능
//          let{} 에는 not null 만 들어올 수 있어서 not null 체크 시 유용함.
//          객체를 선언할 때는 엘비스 연산자 (?:) 로 기본값 지정도 가능
//          run : safe call(?.) 을 붙여서 not null 일 때에만 실행 가능
//          값을 계산하거나 지역범수 범위 제한 시 사용
          result?.let { originalDate ->
            val originalFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN)
            val targetFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN)
            val cal = Calendar.getInstance()

            try {
//              db 가 가져온 날짜에서 1년 더해서 flightDepartureDate 에 저장
              val date: Date = originalFormat.parse(originalDate)!!
              cal.time = date
              cal.add(Calendar.YEAR, 1)
              val flightDepartureDate = targetFormat.format(cal.time)

//              runOnUiThread : 현재 스레드가 Ui 스레드일 경우 작업 즉시 실행
              runOnUiThread {
                val recommendStartDate = findViewById<TextView>(R.id.recommend_start_date)
                recommendStartDate.text = flightDepartureDate

//                별로에요 누르면 다시 main으로
                val badRecommendButton = findViewById<Button>(R.id.bad_recommend_button)
                badRecommendButton.setOnClickListener {
                  val intent = Intent(this@GoAirplaneActivity, MainActivity::class.java)
                  startActivity(intent)
                }

                val goodRecommendButton = findViewById<Button>(R.id.good_recommend_button)
                goodRecommendButton.setOnClickListener {
                  val intent = Intent(this@GoAirplaneActivity, GoAirplaneActivity::class.java)
                  intent.putExtra("출발지", selectedDeparture)
                  intent.putExtra("도착지", selectedArrive)
                  intent.putExtra("출발일", flightDepartureDate)
                  intent.putExtra("도착일", flightDepartureDate)
                  intent.putExtra("인원수", selectedPeople)
                  intent.putExtra("왕복 선택 여부", roundTripChecked)

//                  새 출발일으로 다시 시작
                  startActivity(intent)
                }
              }
            } catch (e: Exception) {
              e.printStackTrace()
              runOnUiThread {
                val recommendStartDate = findViewById<TextView>(R.id.recommend_start_date)
                recommendStartDate.text = "날짜 변환 오류"
              }
            }
//            내가 받아온 값이 null 이면 result is null 로그에 출력
          } ?: run {
            Log.d("flightLog", "result is null")
          }
        } else {
          Log.d("flightLog", "response failed : ${res.errorBody()?.string()}")
        }
      }

      override fun onFailure(
        p0: Call<ResponseBody>,
        t: Throwable
      ) {
        Log.d("flightLog", "message : $t.message")
      }
    })
  }
}

//뷰홀더
class MyViewHolderGoAirplane(val binding: ItemGoAirplaneBinding) :
  RecyclerView.ViewHolder(binding.root)

//어댑터
class MyAdapterGoAirplane(val datas: MutableList<flightInfoDTO>) :
  RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): RecyclerView.ViewHolder {
    return MyViewHolderGoAirplane(
      ItemGoAirplaneBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  @SuppressLint("SetTextI18n")
  override fun onBindViewHolder(
    holder: RecyclerView.ViewHolder,
    position: Int
  ) {
    val binding = (holder as MyViewHolderGoAirplane).binding
    val item = datas[position]

    binding.goAirplaneAirline.text = item.flightInfoAirline

//    내가 db에서 받아온 네자리 시간값(0000) 을 두자리로 나눠서(00 00) 그 사이에 : 를 넣는다 (00:00)
    binding.goAirplaneStartTime.text = item.flightInfoStartTime.chunked(2).joinToString(":")
    binding.goAirplaneArrivalTime.text = item.flightInfoArrivalTime.chunked(2).joinToString(":")

//    한국 통화 형식으로 숫자(format 의 매개변수 number) 를 바꾼다
    binding.goAirplaneMoney.text =
      NumberFormat.getInstance(Locale.KOREA).format(item.flightDistance * 500.toInt())

//    각 버튼마다 고유한 값을 유지하기 위해 안에 넣음
    val goAirplaneFlightId = item.flightId
    val distance = item.flightDistance

//    goAirplaneMoney 를 누르면 GoAirplaneChooseSeatActivity 로
    binding.goAirplaneMoney.setOnClickListener {
//      MyViewHolderGoAirplane 의 binding 의 뿌리 객체(item_go_airplane)를 반환함
      val context = binding.root.context
      val intent = Intent(context, GoAirplaneChooseSeatActivity::class.java)

      intent.putExtra("비행기 아이디", goAirplaneFlightId)
      intent.putExtra("출발지", selectedDeparture)
      intent.putExtra("도착지", selectedArrive)
      intent.putExtra("출발일", goDate.toString())
      intent.putExtra("도착일", comeDate.toString())
      intent.putExtra("인원수", selectedPeople)
      intent.putExtra("거리", distance)
      intent.putExtra("왕복 선택 여부", roundTripChecked)

//      item_go_airplane 에서 intent(GoAirplaneChooseSeatActivity)로 이동
      context.startActivity(intent)
    }
  }

  override fun getItemCount(): Int {
    return datas.size
  }
}