package bitc.fullstack.FlightLog.sidebar

// java/bitc/fullstack/FlightLog/sidebar/LoginActivity.kt


// 로그인 액티비티
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import bitc.fullstack.FlightLog.R
import bitc.fullstack.FlightLog.appserver.AppServerClass
import bitc.fullstack.FlightLog.databinding.ActivityLoginBinding
import bitc.fullstack.FlightLog.dto.LoginResponse
import bitc.fullstack.FlightLog.flightmain.JoinMemberActivity
import bitc.fullstack.FlightLog.flightmain.MainActivity
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private var userId: String = ""
private var userKoFirstName: String = ""
private var userKoLastName: String = ""

class LoginActivity : AppCompatActivity() {
    //    툴바 적용 완료
    //  액션바 토글
    private lateinit var toggle: ActionBarDrawerToggle

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    //  쿠키 저장
    private val cookieManager by lazy { CookieManager.getInstance() }


    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.LoginPage) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getExtra()
        initEventListener()
        navViewFunc()

//    쿠키 확인용
        cookieManager.apply {
            this.acceptCookie()
        }
    }

    private fun getExtra() {
        userId = getCookie("userId").toString()
        userKoFirstName = getCookie("userKoFirstName").toString()
        userKoLastName = getCookie("userKoLastName").toString()
        Log.d("flightLog", "--------------LoginActivity----------------")
        Log.d("flightLog", "hasCookies : ${hasCookies()}")
        Log.d("flightLog", "userId : $userId")
        Log.d("flightLog", "userKoFirstName : $userKoFirstName")
        Log.d("flightLog", "userKoLastName : $userKoLastName")
    }

    //    로그인 이벤트 활성화 함수
    private fun initEventListener() {

        // 로그인 버튼 이벤트
        binding.btnLogin.setOnClickListener {

            //  사용자가 입력한 로그인  id/pw
            val inputUserId = binding.inputUserId.text.toString()
            val inputUserPw = binding.inputUserPw.text.toString()

            //  로그인 네트워크 연동 선언과 적용
            val api = AppServerClass.instance
            val call = api.flightLogin(inputUserId, inputUserPw)
            retrofitResponse(call)
        }

        binding.signUpButton.setOnClickListener {
            val intent = Intent(this, JoinMemberActivity::class.java)
            startActivity(intent)
        }
    }

    //  쿠키 있는지 확인용
    private fun hasCookies(): Boolean {
        return cookieManager.hasCookies()
    }


    //  네비게이션 및 드로어뷰 관련
    @SuppressLint("SetTextI18n")
    fun navViewFunc() {
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        val headerView = navView.getHeaderView(0)

        val isLogin = headerView?.findViewById<TextView>(R.id.nav_is_login)
        val loginButton = headerView?.findViewById<TextView>(R.id.nav_login_button)

//    네비게이션 메뉴 아이템 가져오기
        val menu = navView.menu
        val myInfoItem = menu.findItem(R.id.nav_my_info)
        val ticketHolderItem = menu.findItem(R.id.nav_ticket_holder)
        val showReservationItem = menu.findItem(R.id.nav_show_reservation)

//    쿠키가 있으면 다른거 보이게
        if (hasCookies() == true) {
            Log.d("flightLog", "hasCookies() true")
            isLogin?.text = "$userKoFirstName$userKoLastName 님 환영합니다"
            loginButton?.text = "로그아웃"

            myInfoItem.isVisible = true
            ticketHolderItem.isVisible = true
            showReservationItem.isVisible = true
            loginButton?.setOnClickListener {
                Toast.makeText(this@LoginActivity, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()

//        쿠키 삭제
                val callback = ValueCallback<Boolean> {
                    println(if (it) "logout" else "fail to logout")
                }
                cookieManager.removeAllCookies(callback)

//        메인 액티비티 재시작
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                finish()
                startActivity(intent)
            }
        } else {
//      다른거 안보이게
            myInfoItem.isVisible = false
            ticketHolderItem.isVisible = false
            showReservationItem.isVisible = false

//        로그인 창으로
            loginButton?.setOnClickListener {
                val intent = Intent(this@LoginActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        //    메뉴 어디를 누르는지에 따라서 가는 곳이 달라짐
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
//        마이페이지
                R.id.nav_user_info -> {
                    val intent = Intent(this@LoginActivity, MyInfoActivity::class.java)
                    intent.putExtra("userId", userId)
                    startActivity(intent)
                }

//        티켓 홀더
                R.id.nav_ticket_holder -> {
                    val intent = Intent(this@LoginActivity, TicketHolderActivity::class.java)
                    startActivity(intent)
                }

//        예약 조회
                R.id.nav_show_reservation -> {
                    val intent = Intent(this@LoginActivity, NonmemberActivity::class.java)
                    startActivity(intent)
                }

//        메인 화면으로
                R.id.nav_go_to_main -> {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        //    액션바를 쓰되, 타이틀은 안보이게
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

//    액션바 토글 버튼을 쓰기 위해서 사용
        toggle = ActionBarDrawerToggle(
            this, drawerLayout, binding.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
//    드로어 레이아웃과 동기화
        toggle.syncState()

//    색깔 하얀색
        toggle.drawerArrowDrawable.color = ContextCompat.getColor(this, android.R.color.white)
    }

    //  레트로핏 응답 인텐트 전환 함수
    private fun retrofitResponse(call: Call<LoginResponse>) {
        val intent = Intent(this, MainActivity::class.java)

//  로그인 확인 함수
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    Log.d("csy", "result : $result")

//   로그인 성공 시 이름 생성
                    if (result != null && result.success) {
                        val user = result.user
                        val name = user?.flightUserKoLastname ?: "알 수 없음"
                        Toast.makeText(this@LoginActivity, "$name 님 환영합니다", Toast.LENGTH_SHORT)
                            .show()

                        //쿠키에 저장
                        setCookie("userKoFirstName", user?.flightUserKoFirstname)
                        setCookie("userKoLastName", user?.flightUserKoLastname)
                        setCookie("userFirstName", user?.flightUserFirstname)
                        setCookie("userLastName", user?.flightUserLastname)
                        setCookie("userId", user?.flightUserId)

                        // 로그인 성공 → 메인 화면으로 이동
                        startActivity(intent)
                        finish()


//      작성 문제로 로그인 실패 시
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

    //  쿠키에서 Id 값 가져오기
    private fun getCookie(key: String?): String? {
        return cookieManager.getCookie(key)
    }

}