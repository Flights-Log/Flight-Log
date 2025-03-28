package bitc.fullstack.FlightLog.flightchoose

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.FlightLog.R
import bitc.fullstack.FlightLog.appserver.AppServerClass
import bitc.fullstack.FlightLog.databinding.ActivityOneWayReservationBinding
import bitc.fullstack.FlightLog.dto.FlightReservationCheckDTO
import bitc.fullstack.FlightLog.flightmain.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OneWayReservationActivity : AppCompatActivity() {

    private val binding: ActivityOneWayReservationBinding by lazy {
        ActivityOneWayReservationBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initEventListener()
    }

    private fun initEventListener() {

        binding.btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

//    val reno = binding.reservationNumber.text.toString()

        val reservationNumber = "test99"
        val api = AppServerClass.instance
        //      DTO 타입을 서버로 전달
        val call = api.getOneWayReservationCheck(reservationNumber)
        retrofitResponse(call)
    }

    private fun retrofitResponse(call: Call<List<FlightReservationCheckDTO>>) {

        call.enqueue(object : Callback<List<FlightReservationCheckDTO>> {
            override fun onResponse(p0: Call<List<FlightReservationCheckDTO>>, res: Response<List<FlightReservationCheckDTO>>) {
                if (res.isSuccessful) {
                    val result = res.body()
                    Log.d("csy", "result : $result")

                    if (result != null && result.isNotEmpty()) {
                        updateUI(result[0])  // 첫 번째 예약 데이터를 UI에 반영
                    }

                }
                else {
                    Log.d("csy", "송신 실패, 상태 코드: ${res.code()}, 메시지: ${res.message()}")
                    //Log.d("csy", "송신 실패")
                    res.errorBody()?.let { errorBody ->
                        val error = errorBody.string()
                        Log.d("csy", "Error Response: $error")
                    }
                }
            }

            override fun onFailure(p0: Call<List<FlightReservationCheckDTO>>, t: Throwable) {
                Log.d("csy", "message : $t.message")
            }
        })
    }

    private fun updateUI(reservation: FlightReservationCheckDTO) {
        binding.reservationNumber.setText(reservation.flightReno)
        binding.lastName.setText(reservation.lastName)
        binding.firstName.setText(reservation.firstName)
        binding.startCity.setText(reservation.startCity)
        binding.arrivalCity.setText(reservation.arrivalCity)
        binding.departureDate.setText(reservation.departureDate)
        binding.departureTime.setText(reservation.departureTime)
        binding.arrivalTime.setText(reservation.arrivalTime)
        binding.passengers.setText(reservation.numPassengers.toString())
        binding.seatNumber.setText(reservation.seatNumber)
        binding.passportNumber.setText(reservation.passport)
        binding.luggage.setText(reservation.luggage)

    }

}