package bitc.fullstack.FlightLog.sidebar

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.webkit.CookieManager
import android.webkit.ValueCallback
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
import bitc.fullstack.FlightLog.databinding.ActivityMyInfoBinding
import bitc.fullstack.FlightLog.dto.JoinDTO
import bitc.fullstack.FlightLog.flightmain.MainActivity
import bitc.fullstack.FlightLog.sidebar.LoginActivity
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private var userId: String = ""
private var userKoFirstName: String = ""
private var userKoLastName: String = ""

class MyInfoActivity : AppCompatActivity() {
//    툴바 넣기 완료

    private val binding: ActivityMyInfoBinding by lazy {
        ActivityMyInfoBinding.inflate(layoutInflater)
    }

    //  쿠키 저장
    private val cookieManager by lazy { CookieManager.getInstance() }

    //  액션바 토글
    private lateinit var toggle: ActionBarDrawerToggle

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
        getExtra()
        navViewFunc()

        //    쿠키 확인용
        cookieManager.apply {
            this.acceptCookie()
        }

        // 수정버튼 클릭 시 마이페이지 정보 수정(강산)
        binding.updateButton.setOnClickListener {
            updateUserInfo()
            val intent = Intent(this@MyInfoActivity, MyInfoActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getExtra() {
        userId = getCookie("userId").toString()
        userKoFirstName = getCookie("userKoFirstName").toString()
        userKoLastName = getCookie("userKoLastName").toString()
        Log.d("flightLog", "--------------MyInfoActivity----------------")
        Log.d("flightLog", "hasCookies : ${hasCookies()}")
        Log.d("flightLog", "userId : $userId")
        Log.d("flightLog", "userKoFirstName : $userKoFirstName")
        Log.d("flightLog", "userKoLastName : $userKoLastName")
    }

    //  쿠키 있는지 확인용
    private fun hasCookies(): Boolean {
        return cookieManager.hasCookies()
    }

    //  쿠키에서 Id 값 가져오기
    private fun getCookie(key: String?): String? {
        return cookieManager.getCookie(key)
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
                Toast.makeText(this@MyInfoActivity, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()

//        쿠키 삭제
                val callback = ValueCallback<Boolean> {
                    println(if (it) "logout" else "fail to logout")
                }
                cookieManager.removeAllCookies(callback)

//        메인 액티비티 재시작
                val intent = Intent(this@MyInfoActivity, MainActivity::class.java)
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
                val intent = Intent(this@MyInfoActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        //    메뉴 어디를 누르는지에 따라서 가는 곳이 달라짐
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
//        마이페이지
                R.id.nav_user_info -> {
                    val intent = Intent(this@MyInfoActivity, MyInfoActivity::class.java)
                    intent.putExtra("userId", userId)
                    startActivity(intent)
                }

//        티켓 홀더
                R.id.nav_ticket_holder -> {
                    val intent = Intent(this@MyInfoActivity, TicketHolderActivity::class.java)
                    startActivity(intent)
                }

//        예약 조회
                R.id.nav_show_reservation -> {
                    val intent = Intent(this@MyInfoActivity, NonmemberActivity::class.java)
                    startActivity(intent)
                }

//        메인 화면으로
                R.id.nav_go_to_main -> {
                    val intent = Intent(this@MyInfoActivity, MainActivity::class.java)
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
