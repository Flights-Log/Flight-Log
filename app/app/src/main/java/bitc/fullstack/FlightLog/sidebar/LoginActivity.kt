package bitc.fullstack.FlightLog.sidebar

// java/bitc/fullstack/FlightLog/sidebar/LoginActivity.kt


// 로그인 액티비티


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.CookieManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.FlightLog.R
import bitc.fullstack.FlightLog.appserver.AppServerClass
import bitc.fullstack.FlightLog.databinding.ActivityLoginBinding
import bitc.fullstack.FlightLog.dto.LoginResponse
import bitc.fullstack.FlightLog.flightmain.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    //  쿠키 저장
    private val cookieManager by lazy { CookieManager.getInstance() }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.LoginPage) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initEventListener()

        cookieManager.apply {
            this.acceptCookie()
        }

    }

//    로그인 이벤트 활성화 함수
    private fun initEventListener(){

        // 로그인 버튼 이벤트
        binding.btnLogin.setOnClickListener {

            //  사용자가 입력한 로그인  id/pw
            val inputUserId = binding.inputUserId.text.toString()
            val inputUserPw = binding.inputUserPw.text.toString()


    //            로그인 네트워크 연동 선언과 적용
            val api = AppServerClass.instance
            val call = api.flightLogin(inputUserId, inputUserPw)
            retrofitResponse(call)

        }
    }



//           레트로핏 응답 인텐트 전환 함수
    private fun retrofitResponse(call: Call<LoginResponse>) {
        val intent = Intent(this, MainActivity::class.java)

//            로그인 확인 함수
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    Log.d("csy", "result : $result")

//                    로그인 성공 시 이름 생성
                    if (result != null && result.success) {
                        val user = result.user
                        val name = user?.flightUserKoLastname ?: "알 수 없음"
                        Toast.makeText(this@LoginActivity, "$name 님 환영합니다", Toast.LENGTH_SHORT).show()

                        //            쿠키에 저장
                        setCookie("userFirstName", user?.flightUserKoFirstname)
                        setCookie("userLastName", user?.flightUserKoLastname)
                        setCookie("userId", user?.flightUserId)

                        // 로그인 성공 → 메인 화면으로 이동
                        startActivity(intent)
                        finish()


//                        작성 문제로 로그인 실패 시
                    } else {
                        val msg = result?.message ?: "로그인 실패"
                        Toast.makeText(this@LoginActivity, msg, Toast.LENGTH_SHORT).show()
                    }

//                    서버 오류로 로그인 실패 시
                } else {
                    Log.d("csy", "로그인 성공 실패, 상태 코드: ${response.code()}, 메시지: ${response.message()}")
                    Toast.makeText(this@LoginActivity, "서버 오류로 로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }

//            네트워크 오류로 로그인 실패 시
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("csy", "네트워크 오류: ${t.message}")
                Toast.makeText(this@LoginActivity, "통신 오류 발생", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun setCookie(key: String?, value: String?) {
        cookieManager.setCookie(key, value)
    }


}