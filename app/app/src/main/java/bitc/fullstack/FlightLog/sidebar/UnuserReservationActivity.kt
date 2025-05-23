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
import bitc.fullstack.FlightLog.databinding.ActivityUnuserReservationBinding
import bitc.fullstack.FlightLog.dto.FlightReservationCheckDTO
import bitc.fullstack.FlightLog.flightmain.MainActivity
import com.google.android.material.navigation.NavigationView

class UnuserReservationActivity : AppCompatActivity() {
//    툴바 적용 완료
    private val binding: ActivityUnuserReservationBinding by lazy {
        ActivityUnuserReservationBinding.inflate(layoutInflater)
    }

    //  쿠키 저장
    private val cookieManager by lazy { CookieManager.getInstance() }

    //  액션바 토글
    private lateinit var toggle: ActionBarDrawerToggle

    private var userId: String = ""
    private var userKoFirstName: String = ""
    private var userKoLastName: String = ""


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
        getExtra()
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

        Log.d("flightLog", "----------------JoinMemberActivity--------------")
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
                Toast.makeText(this@UnuserReservationActivity, "로그아웃 되었습니다", Toast.LENGTH_SHORT)
                    .show()

//        쿠키 삭제
                val callback = ValueCallback<Boolean> {
                    println(if (it) "logout" else "fail to logout")
                }
                cookieManager.removeAllCookies(callback)

//        메인 액티비티 재시작
                val intent = Intent(this@UnuserReservationActivity, MainActivity::class.java)
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
                val intent = Intent(this@UnuserReservationActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        //    메뉴 어디를 누르는지에 따라서 가는 곳이 달라짐
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
//        마이페이지
                R.id.nav_user_info -> {
                    val intent = Intent(this@UnuserReservationActivity, MyInfoActivity::class.java)
                    intent.putExtra("userId", userId)
                    startActivity(intent)
                }

//        티켓 홀더
                R.id.nav_ticket_holder -> {
                    val intent =
                        Intent(this@UnuserReservationActivity, TicketHolderActivity::class.java)
                    startActivity(intent)
                }

//        예약 조회
                R.id.nav_show_reservation -> {
                    val intent =
                        Intent(this@UnuserReservationActivity, NonmemberActivity::class.java)
                    startActivity(intent)
                }

//        메인 화면으로
                R.id.nav_go_to_main -> {
                    val intent = Intent(this@UnuserReservationActivity, MainActivity::class.java)
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


    private fun initEventListener() {

        val flightMembers =
            intent.getSerializableExtra("flight_data") as? List<FlightReservationCheckDTO>

        if (flightMembers != null && flightMembers.isNotEmpty()) {
            // 데이터가 정상적으로 전달된 경우
            val member = flightMembers[0]  // 첫 번째 항목 가져오기 (한 명만 받은 경우)

            // 로그로 확인
            Log.d("예약 조회 결과", "Flight member: $member")
            Log.d("csy", "Received flight data: $flightMembers")

            // 각 TextView에 데이터 설정
            binding.reservationNumber.setText(member.flightReno)  // 예약 번호
            binding.lastName.setText(member.lastName) // 성
            binding.firstName.setText(member.firstName) // 이름
            binding.seatNumber.setText(member.seatNumber) // 좌석 번호
            binding.passportNumber.setText(member.passport) // 여권 번호
            binding.luggage.setText(member.luggage) // 수하물
            binding.startCity.setText(member.startCity) //출발지
            binding.arrivalCity.setText(member.arrivalCity) //도착지
            binding.departureDate.setText(member.departureDate) //출발날짜
            binding.departureTime.setText(member.departureTime) //출발시간
            binding.arrivalTime.setText(member.arrivalTime) //도착시간
            binding.passengers.setText(member.numPassengers) //인원수

            binding.returnLastName.setText(member.lastName) //성
            binding.returnFirstName.setText(member.firstName) //이름
            binding.returnStartCity.setText(member.returnStartCity) //출발지
            binding.returnArrivalCity.setText(member.returnArrivalCity) //도착지
            binding.returnDepartureDate.setText(member.returnDepartureDate) //출발날짜
            binding.returnDepartureTime.setText(member.returnDepartureTime) //출발시간
            binding.returnArrivalTime.setText(member.returnArrivalTime) //도착시간
            binding.returnReservationNumber.setText(member.flightReno)  // 예약 번호
            binding.returnSeatNumber.setText(member.returnSeatNumber) // 좌석 번호
            binding.returnPassportNumber.setText(member.passport) // 여권 번호
            binding.returnLuggage.setText(member.luggage) // 수하물
            binding.returnPassengers.setText(member.numPassengers) //인원수


        } else {
            Log.e("csy", "No flight data received or list is empty")
        }
        binding.btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}


