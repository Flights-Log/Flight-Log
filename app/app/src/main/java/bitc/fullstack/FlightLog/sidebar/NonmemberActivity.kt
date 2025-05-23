package bitc.fullstack.FlightLog.sidebar

import android.annotation.SuppressLint
import android.content.Intent
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
import bitc.fullstack.FlightLog.data.InterFlightResponse
import bitc.fullstack.FlightLog.databinding.ActivityNonMemberBinding
import bitc.fullstack.FlightLog.dto.FlightReservationCheckDTO
import bitc.fullstack.FlightLog.dto.iFlightDTO
import bitc.fullstack.FlightLog.flightmain.MainActivity
import bitc.fullstack.FlightLog.sidebar.TicketHolderActivity
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private var userId: String = ""
private var userKoFirstName: String = ""
private var userKoLastName: String = ""

class NonmemberActivity : AppCompatActivity() {
//    툴바 넣기 완료
    private val binding: ActivityNonMemberBinding by lazy {
        ActivityNonMemberBinding.inflate(layoutInflater)
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
        getExtra()
        navViewFunc()

        //    쿠키 확인용
        cookieManager.apply {
            this.acceptCookie()
        }

        initEventListener()
    }

    private fun getExtra() {
        userId = getCookie("userId").toString()
        userKoFirstName = getCookie("userKoFirstName").toString()
        userKoLastName = getCookie("userKoLastName").toString()

        Log.d("flightLog", "----------------NonmemberActivity------------")
        Log.d("flightLog", "hasCookie : ${hasCookies()}")
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

    private fun initEventListener() {
        binding.unuserSearch.setOnClickListener {
            Log.d("flightLog", "unuser search")

            // 예약번호
            val inputText = binding.nonmemberReservationNumber.text.toString()
            // 여권번호
            val inputText1 = binding.nonmemberPassnum.text.toString()

            val api = AppServerClass.instance
            val call = api.postUnuserSearch(inputText, inputText1)
            retrofitResponse(call)
        }
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
            isLogin?.text = "${userKoFirstName}${userKoLastName} 님 환영합니다"
            loginButton?.text = "로그아웃"

            myInfoItem.isVisible = true
            ticketHolderItem.isVisible = true
            showReservationItem.isVisible = true
            loginButton?.setOnClickListener {
                Toast.makeText(this@NonmemberActivity, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()

//        쿠키 삭제
                val callback = ValueCallback<Boolean> {
                    println(if (it) "logout" else "fail to logout")
                }
                cookieManager.removeAllCookies(callback)

//        메인 액티비티 재시작
                val intent = Intent(this@NonmemberActivity, MainActivity::class.java)
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
                val intent = Intent(this@NonmemberActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        //    메뉴 어디를 누르는지에 따라서 가는 곳이 달라짐
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
//        마이페이지
                R.id.nav_user_info -> {
                    val intent = Intent(this@NonmemberActivity, MyInfoActivity::class.java)
                    intent.putExtra("userId", userId)
                    startActivity(intent)
                }

//        티켓 홀더
                R.id.nav_ticket_holder -> {
                    val intent = Intent(this@NonmemberActivity, TicketHolderActivity::class.java)
                    startActivity(intent)
                }

//        예약 조회
                R.id.nav_show_reservation -> {
                    val intent = Intent(this@NonmemberActivity, NonmemberActivity::class.java)
                    startActivity(intent)
                }

//        메인 화면으로
                R.id.nav_go_to_main -> {
                    val intent = Intent(this@NonmemberActivity, MainActivity::class.java)
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

    //  Retrofit 통신 응답 부분을 따로 메소드로 분리
    private fun retrofitResponse(call: Call<List<FlightReservationCheckDTO>>) {

        call.enqueue(object : Callback<List<FlightReservationCheckDTO>> {
            override fun onResponse(
                p0: Call<List<FlightReservationCheckDTO>>,
                res: Response<List<FlightReservationCheckDTO>>
            ) {
                if (res.isSuccessful) {
                    val result = res.body()
                    Log.d("csy", "result : $result")

                    if (result != null) {
                        // flightArrId가 null인 경우를 체크 it.flightArrId != null &&
                        val hasReturnFlight = result.any { it.flightArrId != 0 }

                        // flightArrId가 null인 경우 다른 화면으로 전환
                        if (hasReturnFlight) {
                            // 왕복 비행편인 경우
                            // 다른 화면을 보여주고, 그 화면에 맞는 Activity로 전환
                            val intent = Intent(
                                this@NonmemberActivity,
                                UnuserReservationActivity::class.java
                            )
                            intent.putExtra("flight_data", ArrayList(result))
                            startActivity(intent)
                        } else {
                            // 편도 비행편인 경우
                            val intent =
                                Intent(this@NonmemberActivity, ReservationGoActivity::class.java)
                            intent.putExtra("flight_data", ArrayList(result))
                            startActivity(intent)
                        }
                    } else {
                        Log.e("csy", "Result is null!")
                    }
                }
            }

            override fun onFailure(p0: Call<List<FlightReservationCheckDTO>>, t: Throwable) {
                Log.d("csy", "message : $t.message")
            }
        })
    }
}
