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

class ChooseDepartureFragment : DialogFragment() {
  private lateinit var binding: FragmentChooseDepartureBinding
  private val departureList = mutableListOf<String>()
  private lateinit var adapter: MyAdapter

  @SuppressLint("MissingInflatedId", "SetTextI18n")
  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    binding = FragmentChooseDepartureBinding.inflate(layoutInflater)

    val builder = AlertDialog.Builder(requireContext())
    val inflater = requireActivity().layoutInflater
    val view = inflater.inflate(R.layout.fragment_choose_departure, null) // ✅ 여기 수정!

    // RecyclerView 초기화
    val recyclerView = view.findViewById<RecyclerView>(R.id.departure_recycler_view)
    adapter = MyAdapter(departureList)
    recyclerView.adapter = adapter

    // 다이얼로그에 뷰 설정
    builder.setView(view)
      .setTitle("출발지 선택")
      .setPositiveButton("확인") { dialog, _ -> dialog.dismiss() }
      .setNegativeButton("취소", null)

//출발지 장소 리스트로 가져오기
    Log.d("flightLog", "출발지 장소 가져오기")
    val api = AppServerClass.instance
    val call = api.searchDeparture()
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
            departureList.clear()
            departureList.addAll(it)
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

class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
  val departureName: TextView = view.findViewById(R.id.departure_name)
}

class MyAdapter(private val datas: MutableList<String>) : RecyclerView.Adapter<MyViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.item_departure, parent, false)
    return MyViewHolder(view)
  }

  override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    holder.departureName.text = datas[position]
    holder.itemView.setOnClickListener {
      Log.d("flightLog", "선택한 출발지: ${datas[position]}")
    }
  }

  override fun getItemCount(): Int {
    return datas.size
  }
}



