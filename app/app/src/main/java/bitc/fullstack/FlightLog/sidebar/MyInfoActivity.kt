package bitc.fullstack.FlightLog.sidebar

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.FlightLog.appserver.AppServerClass
import bitc.fullstack.FlightLog.databinding.ActivityMyInfoBinding
import bitc.fullstack.FlightLog.dto.JoinDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyInfoActivity : AppCompatActivity() {

    private val binding: ActivityMyInfoBinding by lazy {
        ActivityMyInfoBinding.inflate(layoutInflater)
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
        loadUserInfo()

        // 수정버튼 클릭 시 마이페이지 정보 수정(강산)
        binding.updateButton.setOnClickListener {
            updateUserInfo()
            val intent = Intent(this@MyInfoActivity, MyInfoActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    //    업데이트 함수(강산)
    private fun updateUserInfo() {

        val user: JoinDTO = JoinDTO(
            flightUserKoFirstname = binding.myInfoKoFirstName.text.toString(),
            flightUserKoLastname = binding.myInfoKoLastName.text.toString(),
            flightUserFirstname = binding.myInfoFirstName.text.toString(),
            flightUserLastname = binding.myInfoLastName.text.toString(),
            flightUserGender = binding.myInfoGender.text.toString(),
            flightUserBirth = binding.myInfoBirthDate.text.toString(),
            flightUserPhone = binding.myInfoPhone.text.toString(),
            flightUserEmail = binding.myInfoEmail.text.toString(),
            flightUserId = binding.myInfoId.text.toString(),
            flightUserPw = binding.myInfoPw.text.toString()
        )
        val api = AppServerClass.instance
        val call = api.updateUserInfo(user)
        retrofitResponse(call)
    }


    // 사용자 정보 조회 (SELECT)
    private fun loadUserInfo() {
        val userId: String = intent.getStringExtra("userId").toString()
        val api = AppServerClass.instance
        val call = api.userInfo(userId)
        retrofitResponse(call)
    }


    private fun retrofitResponse(call: Call<List<JoinDTO>>) {
        call.enqueue(object : Callback<List<JoinDTO>> {
            override fun onResponse(call: Call<List<JoinDTO>>, res: Response<List<JoinDTO>>) {
                if (res.isSuccessful) {
                    val result = res.body()
                    Log.d("csy", "result : $result")

                    val user = result?.get(0)

                    binding.myInfoKoLastName.setText(user?.flightUserKoLastname)
                    binding.myInfoKoFirstName.setText(user?.flightUserKoFirstname)
                    binding.myInfoLastName.setText(user?.flightUserLastname)
                    binding.myInfoFirstName.setText(user?.flightUserFirstname)
                    binding.myInfoGender.setText(user?.flightUserGender)
                    binding.myInfoBirthDate.setText(user?.flightUserBirth)
                    binding.myInfoPhone.setText(user?.flightUserPhone)
                    binding.myInfoId.setText(user?.flightUserId)
                    binding.myInfoPw.setText(user?.flightUserPw)

                } else {
                    Log.d("csy", "송신 실패, 상태 코드: ${res.code()}, 메시지: ${res.message()}")
                    res.errorBody()?.let { errorBody ->
                        val error = errorBody.string()
                        Log.d("csy", "Error Response: $error")
                    }
                }
            }

            override fun onFailure(call: Call<List<JoinDTO>>, t: Throwable) {
                Log.d("csy", "message : ${t.message}")
            }
        })


    }
}
