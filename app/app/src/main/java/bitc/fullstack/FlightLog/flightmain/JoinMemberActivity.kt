package bitc.fullstack.FlightLog.flightmain
//java/bitc/fullstack/FlightLog/flightmain/JoinMemberActivity.kt
//회원가입 액티비티

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import bitc.fullstack.FlightLog.R
import bitc.fullstack.FlightLog.appserver.AppServerClass
import bitc.fullstack.FlightLog.databinding.ActivityJoinMemberBinding
import bitc.fullstack.FlightLog.dto.JoinDTO
import bitc.fullstack.FlightLog.sidebar.LoginActivity
import bitc.fullstack.FlightLog.sidebar.MyInfoActivity
import bitc.fullstack.FlightLog.sidebar.NonmemberActivity
import bitc.fullstack.FlightLog.sidebar.TicketHolderActivity
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private var userId: String = ""
private var userKoFirstName: String = ""
private var userKoLastName: String = ""

class JoinMemberActivity : AppCompatActivity() {
//    툴바 넣기 완료
    private val binding: ActivityJoinMemberBinding by lazy {
        ActivityJoinMemberBinding.inflate(layoutInflater)
    }

    //  쿠키 저장
    private val cookieManager by lazy { CookieManager.getInstance() }

    //  액션바 토글
    private lateinit var toggle: ActionBarDrawerToggle

    //  성별 버튼 선택 선언
    var selectedGender: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.joinMember) { v, insets ->
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

//    버튼 뷰 선언(변경 대기중)
        val btnMale = findViewById<AppCompatButton>(R.id.btnMale)
        val btnFemale = findViewById<AppCompatButton>(R.id.btnFemale)
        val editBirthDate = findViewById<EditText>(R.id.editBirthDate)

//    성별 버튼 선택 이벤트(남성)
        binding.btnMale.setOnClickListener {
            selectedGender = "M"
            btnMale.setBackgroundResource(R.drawable.gender_button_selected)
            btnFemale.setBackgroundResource(R.drawable.gender_button_unselected)
            btnMale.setTextColor(Color.WHITE)
            btnFemale.setTextColor(Color.BLACK)
        }

//    성별 버튼 선택 이벤트(여성)
        binding.btnFemale.setOnClickListener {
            selectedGender = "F"
            btnFemale.setBackgroundResource(R.drawable.gender_button_selected)
            btnMale.setBackgroundResource(R.drawable.gender_button_unselected)
            btnFemale.setTextColor(Color.WHITE)
            btnMale.setTextColor(Color.BLACK)
        }

//    생년월일 선택 이벤트
        binding.editBirthDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val birthDate =
                    String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
                editBirthDate.setText(birthDate)
            }, year, month, day)

            datePicker.show()
        }


//    회원가입 버튼(누를 시, 회원가입 데이터 생성)
        binding.btnJoin.setOnClickListener {
            //val joinDataList = mutableListOf<JoinDTO>()

            val joinData = JoinDTO(
                flightUserId = binding.editId.text.toString(),
                flightUserEmail = binding.editEmail.text.toString(),
                flightUserPw = binding.editPw.text.toString(),
                flightUserFirstname = binding.editFirstName.text.toString(),
                flightUserLastname = binding.editLastName.text.toString(),
                flightUserKoFirstname = binding.editKoFirstName.text.toString(),
                flightUserKoLastname = binding.editKoLastName.text.toString(),
                flightUserPhone = binding.editPhone.text.toString(),
                flightUserGender = selectedGender ?: "",
                flightUserBirth = binding.editBirthDate.text.toString()
            )
            //joinDataList.add(joinData)


//      네트워크 통신
            val api = AppServerClass.instance
            val call = api.joinMember(joinData)
            retrofitResponse(call)

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
                Toast.makeText(this@JoinMemberActivity, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()

//        쿠키 삭제
                val callback = ValueCallback<Boolean> {
                    println(if (it) "logout" else "fail to logout")
                }
                cookieManager.removeAllCookies(callback)

//        메인 액티비티 재시작
                val intent = Intent(this@JoinMemberActivity, MainActivity::class.java)
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
                val intent = Intent(this@JoinMemberActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        //    메뉴 어디를 누르는지에 따라서 가는 곳이 달라짐
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
//        마이페이지
                R.id.nav_user_info -> {
                    val intent = Intent(this@JoinMemberActivity, MyInfoActivity::class.java)
                    intent.putExtra("userId", userId)
                    startActivity(intent)
                }

//        티켓 홀더
                R.id.nav_ticket_holder -> {
                    val intent = Intent(this@JoinMemberActivity, TicketHolderActivity::class.java)
                    startActivity(intent)
                }

//        예약 조회
                R.id.nav_show_reservation -> {
                    val intent = Intent(this@JoinMemberActivity, NonmemberActivity::class.java)
                    startActivity(intent)
                }

//        메인 화면으로
                R.id.nav_go_to_main -> {
                    val intent = Intent(this@JoinMemberActivity, MainActivity::class.java)
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

    //  쿠키에서 Id 값 가져오기
    private fun getCookie(key: String?): String? {
        return cookieManager.getCookie(key)
    }

    //  송수신 점검 코드
    private fun retrofitResponse(call: Call<String>) {

        call.enqueue(object : Callback<String> {
            override fun onResponse(p0: Call<String>, res: Response<String>) {
                if (res.isSuccessful) {
                    val result = res.body()
                    Log.d("csy", "result : $result")
                } else {
                    Log.d("csy", "송신 실패, 상태 코드: ${res.code()}, 메시지: ${res.message()}")
                    //Log.d("csy", "송신 실패")
                    res.errorBody()?.let { errorBody ->
                        val error = errorBody.string()
                        Log.d("csy", "Error Response: $error")
                    }
                }
            }

            override fun onFailure(p0: Call<String>, t: Throwable) {
                Log.d("csy", "message : $t.message")
            }
        })
    }
}