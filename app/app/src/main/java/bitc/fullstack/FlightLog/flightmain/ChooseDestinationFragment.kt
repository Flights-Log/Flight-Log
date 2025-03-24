package bitc.fullstack.FlightLog.flightmain

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import bitc.fullstack.FlightLog.R
import bitc.fullstack.FlightLog.appserver.AppServerClass
import bitc.fullstack.FlightLog.databinding.FragmentChooseDepartureBinding
import bitc.fullstack.FlightLog.databinding.FragmentChooseDestinationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChooseDestinationFragment : DialogFragment() {
  private lateinit var binding: FragmentChooseDestinationBinding
  private val destinationList = mutableListOf<String>()
  private lateinit var adapter: MyAdapter
  private var listener: OnDestinationSelectedListener? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
//    MainActivity. 가 구현됐는지 확인하고 listener 에 저장
    listener = activity as? OnDestinationSelectedListener
  }

  @SuppressLint("MissingInflatedId", "SetTextI18n")
  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    binding = FragmentChooseDestinationBinding.inflate(layoutInflater)

    val builder = AlertDialog.Builder(requireContext())
    val inflater = requireActivity().layoutInflater
    val view = inflater.inflate(R.layout.fragment_choose_destination, null)

//    리사이클러 뷰 찾기
    val recyclerView = view.findViewById<RecyclerView>(R.id.destination_recycler_view)
//    출발지의 목록 중에서 내가 값을 선택하면 창 닫기
    adapter = MyAdapter(destinationList) { selectedDestination ->
      listener?.onDestinationSelected(selectedDestination)
      dismiss()
    }
    recyclerView.adapter = adapter

    // 다이얼로그에 뷰 설정
    builder.setView(view)
      .setTitle("출발지 선택")
      .setNegativeButton("취소", null)

//출발지 장소 리스트로 가져오기
    val api = AppServerClass.instance
    val call = api.searchDestination()
    retrofitResponse(call)

    return builder.create()
  }

  //  Retrofit 통신 응답 List<String>
  private fun retrofitResponse(call: Call<List<String>>) {
    call.enqueue(object : Callback<List<String>> {
      @SuppressLint("NotifyDataSetChanged")
      override fun onResponse(p0: Call<List<String>>, res: Response<List<String>>) {
        if (res.isSuccessful) {
          val result = res.body()
          Log.d("flightLog", "result : $result")

          result?.let {
            destinationList.clear()
            destinationList.addAll(it)
            adapter.notifyDataSetChanged()  // RecyclerView 업데이트
          }
        } else {
          Log.d("flightLog", "송신 실패")
        }
      }

      override fun onFailure(p0: Call<List<String>>, t: Throwable) {
        Log.d("flightLog", "message : $t.message")
      }
    })
  }
}

//뷰 홀더 클래스
class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
  val destinationName: TextView = view.findViewById(R.id.destination_name)
}

//어댑터
class MyAdapter(
  private val datas: MutableList<String>,
  private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<MyViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.item_destination, parent, false)
    return MyViewHolder(view)
  }

  //  아이템 뷰(출발지 목록 중에서 하나 골라서 누르면) 그 값을 MainActivity 에 전달하기
  override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    holder.destinationName.text = datas[position]
    holder.itemView.setOnClickListener {
      Log.d("flightLog", "선택한 출발지: ${datas[position]}")
      onItemClick(datas[position])
    }
  }

  //  데이터 사이즈 주기
  override fun getItemCount(): Int {
    return datas.size
  }
}

//destination 값을 MainActivity 로 전달하기 위해서 추가한 interface
//MainActivity 에서 interface 를 상속받아서 출발지 텍스트 바꿈
interface OnDestinationSelectedListener {
  fun onDestinationSelected(destination: String)
}



