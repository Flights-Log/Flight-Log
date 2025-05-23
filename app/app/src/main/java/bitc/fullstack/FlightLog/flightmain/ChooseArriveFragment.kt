package bitc.fullstack.FlightLog.flightmain

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import bitc.fullstack.FlightLog.R
import bitc.fullstack.FlightLog.appserver.AppServerClass
import bitc.fullstack.FlightLog.databinding.FragmentChooseArriveBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChooseArriveFragment : DialogFragment() {
  //  바인딩
  private lateinit var binding: FragmentChooseArriveBinding

  //  도착지 목록 저장
  private val arriveList = mutableListOf<String>()

  //  recyclerView 어뎁터
  private lateinit var adapter: MyAdapterArrive

  //  선택된 도착지 전달 인터페이스
  private var listener: OnArriveSelectedListener? = null

  //  기본 출발지
  private var selectedDeparture: String = ""

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
//    MainActivity. 가 구현됐는지 확인하고 listener 에 저장
//    나중에 도착지를 선택하면 listener?.onArriveSelected(도착지) 를 호출해서 Main 에 전달
    listener = activity as? OnArriveSelectedListener
  }

  //출발지 설정 함수
//  selectedDeparture 에 내가 main 에서 설정한 출발지 값을 넣는다
  fun setSelectedDeparture(departure: String) {
    selectedDeparture = departure
  }

  @SuppressLint("MissingInflatedId", "SetTextI18n")
  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    binding = FragmentChooseArriveBinding.inflate(layoutInflater)
    val builder = AlertDialog.Builder(requireContext())
    // 다이얼로그에 뷰 설정
    builder.setView(binding.root)
      .setTitle("도착지 선택")
      .setNegativeButton("취소", null)

//    리사이클러 뷰 찾아서 어댑터와 연결하기
    val recyclerView = binding.arriveRecyclerView

//    도착지를 클릭하면 listener 의 onArrivedSelecte 호출.
//    MainActivity 에서 선택한 도착지 받음
//    도착지를 선택하면 다이얼로그 닫기(dismiss())
    adapter = MyAdapterArrive(arriveList) { selectedArrive ->
      listener?.onArriveSelected(selectedArrive)
      dismiss()
    }
    recyclerView.adapter = adapter

//도착지 장소 리스트로 가져오기
    val api = AppServerClass.instance
    Log.d("flightLog", "출발지 : $selectedDeparture")

    val call = api.searchArrive(selectedDeparture)
    retrofitResponse(call)

    return builder.create()
  }

  //  Retrofit 통신 응답 List<String>
  private fun retrofitResponse(call: Call<List<String>>) {
    call.enqueue(object : Callback<List<String>> {

      //서버 응답이 성공하면 result 에 저장하고 arriveList에 추가
      @SuppressLint("NotifyDataSetChanged")
      override fun onResponse(p0: Call<List<String>>, res: Response<List<String>>) {
        if (res.isSuccessful) {
          val result = res.body()
//          Log.d("flightLog", "result : $result")

          result?.let {
            arriveList.clear()
            arriveList.addAll(it)
            // RecyclerView 업데이트
            adapter.notifyDataSetChanged()

//            만약에 그 공항에서 출발하는 도착지가 하나도 없을 경우 출력
            val noArriveText = binding.noArriveText
            if (arriveList.isEmpty()) {
              noArriveText.visibility = View.VISIBLE
            } else {
              noArriveText.visibility = View.GONE
            }

          }
        } else {
          Log.d("flightLog", "송신 실패 ")
        }
      }

      override fun onFailure(p0: Call<List<String>>, t: Throwable) {
        Log.d("flightLog", "message : $t.message")
      }
    })
  }
}

//뷰 홀더 클래스. 도착지 이름을 표시할 TextView 연결
class MyViewHolderArrive(view: View) : RecyclerView.ViewHolder(view) {
  val arriveName: TextView = view.findViewById(R.id.arrive_name)
}

//어댑터
class MyAdapterArrive(
//  도착지 목록 리스트
  private val datas: MutableList<String>,
//  도착지 선택 시 호출될 함수
  private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<MyViewHolderArrive>() {

  //  레이아웃 설정
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderArrive {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.item_arrive, parent, false)
    return MyViewHolderArrive(view)
  }

  //  아이템 뷰(도착지 목록 중에서 하나 골라서 누르면) 그 값을 MainActivity 에 전달하기
  override fun onBindViewHolder(holder: MyViewHolderArrive, position: Int) {
    holder.arriveName.text = datas[position]
    holder.itemView.setOnClickListener {
      Log.d("flightLog", "선택한 도착지: ${datas[position]}")
//      도착지 선택 시 호출
      onItemClick(datas[position])
    }
  }

  //  데이터 사이즈 주기
  override fun getItemCount(): Int {
    return datas.size
  }
}

//arrive 값을 MainActivity 로 전달하기 위해서 추가한 interface
//MainActivity 에서 interface 를 상속받아서 도착지 텍스트 바꿈
interface OnArriveSelectedListener {
  fun onArriveSelected(arrive: String)
}



