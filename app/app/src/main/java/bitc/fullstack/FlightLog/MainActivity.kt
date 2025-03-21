package bitc.fullstack.FlightLog

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.FlightLog.databinding.ActivityMainBinding
<<<<<<< HEAD

class MainActivity : AppCompatActivity() {
  private val binding:ActivityMainBinding by lazy{
    ActivityMainBinding.inflate(layoutInflater)
  }
=======
import bitc.fullstack.app.AppServerClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

  private val binding: ActivityMainBinding by lazy {
    ActivityMainBinding.inflate(layoutInflater)
  }

>>>>>>> 4be9a0c33f0cb71f83c17aeb8643d6871b85f335
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(binding.root)
    ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
<<<<<<< HEAD

    binding.btnPassenger.setOnClickListener {
      val intent = Intent(this,PassengerActivity::class.java)
      startActivity(intent)
    }
=======
    initEventListener()
  }
  private fun initEventListener() {
    //    기본 GET 방식 통신, 파라미터 없음
    binding.btnGet1.setOnClickListener {
      Log.d("csy", "gettest1 시작")
      val api = AppServerClass.instance
      val call = api.getTest1()
      retrofitResponse(call)
    }

  }

//  Retrofit 통신 응답 부분을 따로 메소드로 분리
  private fun retrofitResponse(call: Call<String>) {
    call.enqueue(object : Callback<String>{
      override fun onResponse(p0: Call<String>, res: Response<String>) {
        if (res.isSuccessful) {
          val result = res.body()
          Log.d("flightLog", "result : $result")
        }
        else {
          Log.d("flightLog", "송신 실패")
        }
      }

      override fun onFailure(p0: Call<String>, t: Throwable) {
        Log.d("flightLog", "message : $t.message")
      }
    })
>>>>>>> 4be9a0c33f0cb71f83c17aeb8643d6871b85f335
  }
}

