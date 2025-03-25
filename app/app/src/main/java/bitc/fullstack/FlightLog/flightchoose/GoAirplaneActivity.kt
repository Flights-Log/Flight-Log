package bitc.fullstack.FlightLog.flightchoose

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bitc.fullstack.FlightLog.appserver.AppServerClass
import bitc.fullstack.FlightLog.databinding.ActivityGoAirplaneBinding
import bitc.fullstack.FlightLog.databinding.ItemGoAirplaneBinding
import bitc.fullstack.FlightLog.dto.flightInfoDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.format.DateTimeFormatter

class GoAirplaneActivity : AppCompatActivity() {
  //  ActivityGoAirplaneBinding
  private val binding: ActivityGoAirplaneBinding by lazy {
    ActivityGoAirplaneBinding.inflate(layoutInflater)
  }

  //  내가 검색한 항공기의 이름, 출발 시간, 도착 시간, 가격을 저장함
  private val goAirplaneInfoList = mutableListOf<flightInfoDTO>()

  //  어댑터
  private lateinit var adapter: MyAdapterGoAirplane

  //  출발도시, 도착도시, 가는날
  var startCity: String = ""
  var arrivalCity: String = ""
  var goDate: String = ""

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(binding.root)

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
    val call = api.searchGoAirplane(startCity, arrivalCity, goDate)
    retrofitResponse(call)
  }

  //  내가 MainActivity 에서 intent 로 받아온 값 화면과 로그에 출력
//  그리고 위에 있는 startCity, arrivalCity, goDate 에 넣어줌
  fun getExtra() {
    binding.textStartCity.text = intent.getStringExtra("출발지")
    startCity = binding.textStartCity.text.toString()

    binding.textArrivalCity.text = intent.getStringExtra("도착지")
    arrivalCity = binding.textArrivalCity.text.toString()

    binding.flightDepartureDate.text = intent.getStringExtra("출발일")
    goDate = binding.flightDepartureDate.text.toString()

    Log.d("flightLog", "받은 도착일 : ${intent.getStringExtra("도착일")}")
  }

  //  Retrofit 통신 응답 List<String>
  private fun retrofitResponse(call: Call<List<flightInfoDTO>>) {
    call.enqueue(object : Callback<List<flightInfoDTO>> {
      @SuppressLint("NotifyDataSetChanged")
      override fun onResponse(p0: Call<List<flightInfoDTO>>, res: Response<List<flightInfoDTO>>) {
        if (res.isSuccessful) {
          val result = res.body()
          Log.d("flightLog", "result : $result")

          result?.let {
            goAirplaneInfoList.clear()
            goAirplaneInfoList.addAll(it)
            adapter.notifyDataSetChanged()  // RecyclerView 업데이트
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
}


class MyViewHolderGoAirplane(val binding: ItemGoAirplaneBinding) :
  RecyclerView.ViewHolder(binding.root)

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

    var hour = item.flightInfoStartTime
    hour.chunked(2)
    Log.d("flightLog", "hour : $hour")

    binding.goAirplaneStartTime.text = item.flightInfoStartTime
    binding.goAirplaneArrivalTime.text = item.flightInfoArrivalTime
    binding.goAirplaneMoney.text = "50000"
  }

  override fun getItemCount(): Int {
    return datas.size
  }

}