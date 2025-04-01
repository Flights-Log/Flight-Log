package bitc.fullstack.FlightLog.flightchoose

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bitc.fullstack.FlightLog.R
import bitc.fullstack.FlightLog.appserver.AppServerClass
import bitc.fullstack.FlightLog.dto.FlightReservationCheckDTO
import bitc.fullstack.FlightLog.flightchoose.FlightReservationAdapter
import bitc.fullstack.FlightLog.flightmain.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReservationCheckActivity : AppCompatActivity() {

  private lateinit var recyclerView: RecyclerView
  private lateinit var adapter: FlightReservationAdapter
  private val flightReservationList: MutableList<FlightReservationCheckDTO> = mutableListOf()


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_reservation_check)

    val flightUserId = intent.getStringExtra("flightUserId") ?: ""
    Log.d("ReservationCheck", "받은 flightUserId: $flightUserId")

    recyclerView = findViewById(R.id.recyclerView)
    recyclerView.layoutManager = LinearLayoutManager(this)

    // Adapter 설정
    adapter = FlightReservationAdapter(flightReservationList)
    recyclerView.adapter = adapter

    // 예약 정보 API 호출
    fetchReservationDetails(flightUserId)

    val home = findViewById<Button>(R.id.btn_home)
    home.setOnClickListener {
      val intent = Intent(this, MainActivity::class.java)
      startActivity(intent)
    }
  }

  private fun fetchReservationDetails(flightUserId: String) {
    val apiService = AppServerClass.instance

    apiService.getReservationCheck(flightUserId).enqueue(object : Callback<List<FlightReservationCheckDTO>> {
      override fun onResponse(call: Call<List<FlightReservationCheckDTO>>, response: Response<List<FlightReservationCheckDTO>>) {
        if (response.isSuccessful) {
          val reservationData = response.body()
          if (reservationData != null) {
            flightReservationList.clear()
            flightReservationList.addAll(reservationData)
            adapter.notifyDataSetChanged() // 데이터 갱신
          } else {
            Toast.makeText(this@ReservationCheckActivity, "예약 정보가 없습니다.", Toast.LENGTH_SHORT).show()
          }
        } else {
          Toast.makeText(this@ReservationCheckActivity, "데이터 가져오기 실패", Toast.LENGTH_SHORT).show()
        }
      }

      override fun onFailure(call: Call<List<FlightReservationCheckDTO>>, t: Throwable) {
        Toast.makeText(this@ReservationCheckActivity, "네트워크 오류", Toast.LENGTH_SHORT).show()
      }
    })
  }
}
