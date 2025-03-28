package bitc.fullstack.FlightLog.flightmain


// java/bitc/fullstack/FlightLog/flightmain/MainActivity.kt


// 메인 액티비티


import android.R.id.toggle
import android.app.DatePickerDialog
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import bitc.fullstack.FlightLog.R
import bitc.fullstack.FlightLog.databinding.ActivityMainBinding
import bitc.fullstack.FlightLog.dto.JoinDTO
import com.google.android.material.navigation.NavigationView
import java.time.LocalDate
import java.util.Calendar

open class MainActivity  : AppCompatActivity(), SelectPeopleDialogFragment.OnPassengerSelectedListener {

    //  ActivityMainBinding
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    //  현재 날짜값 및 도착 예정 날짜값
    private var goDate = LocalDate.now()
    private var comeDate = goDate.plusWeeks(1)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)


        //    툴바 아이콘(뷰 바인딩)
//    val menuButton: ImageButton = findViewById(R.id.flight_log_menu)
//    val iconButton: ImageView = findViewById(R.id.flight_log_icon)
//    val loginButton: TextView = findViewById(R.id.flight_log_login)


        ViewCompat.setOnApplyWindowInsetsListener(binding.main)
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //  로그인한 사용자 정보 사이드바에 출력
        sidebarInfo()

//    가는날 오는날 텍스트, 날짜 선택
        chooseGoDate()
        chooseComeDate()

//    인원 선택 창으로 가는 함수
        chooseSelectPeople()

//    출발지 설정
        chooseDeparture()

//    도착지 설정
        chooseDestination()
    }
    // 사이드바 관련
    fun sidebarInfo() {
        val loginUser = intent.getSerializableExtra("login_user") as? JoinDTO
        loginUser?.let {
            val name = it.flightUserKoLastname
            binding.loginStatus.setText("$name 님 환영합니다")
        } ?: run {
            binding.loginStatus.setText("로그인 사용자 없음")
        }
    }


        //  가는 날 텍스트(chooseGoDateText) 관련 함수
        fun chooseGoDate() {
//    날짜 출력
            Log.d("flightLog", "goDate : $goDate")
            binding.chooseGoDateText.text = goDate.toString()

//    누르면 캘린더 뷰 나옴
            binding.chooseGoDateText.setOnClickListener {
//      현재 시간기반 달력 얻어오기
                val calendar = Calendar.getInstance()

//      날짜 선택하는 상자 만들기
                val datePickerDialog = DatePickerDialog(
                    this,
//        LocalDate 에서 현재 기기의 연월일 가져오기
                    { _, year, month, dayOfMonth ->
                        val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)

//          가는 날 변경 시 오는 날도 최소 한 주 뒤로 자동 업데이트
                        goDate = selectedDate
                        if (comeDate.isBefore(goDate.plusWeeks(1))) {
                            comeDate = goDate.plusWeeks(1)
                            binding.chooseComeDateText.text = comeDate.toString()
                        }
                        binding.chooseGoDateText.text = goDate.toString()
                    },
                    goDate.year,
                    goDate.monthValue - 1,
                    goDate.dayOfMonth
                )

//      선택 가능한 범위 지정
                datePickerDialog.datePicker.minDate = System.currentTimeMillis()
                calendar.add(Calendar.MONTH, 6)
                datePickerDialog.datePicker.maxDate = calendar.timeInMillis

                datePickerDialog.show()
            }
        }

        //  오는 날 텍스트(chooseComeDateText, chooseComeDateArrow) 관련 함수
        fun chooseComeDate() {
            Log.d("flightLog", "comeDate : $comeDate")
            binding.chooseComeDateText.text = comeDate.toString()

//    누르면 캘린더 뷰 나옴
            binding.chooseComeDateText.setOnClickListener {
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = System.currentTimeMillis()

                val datePickerDialog = DatePickerDialog(
                    this,
                    { _, year, month, dayOfMonth ->
                        val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)

//          오는 날이 가는 날 전인지 체크
                        if (selectedDate.isBefore(goDate)) {
                            showAlert("오는 날은 가는 날 이후여야 합니다!")
                        } else {
                            comeDate = selectedDate
                            binding.chooseComeDateText.text = comeDate.toString()
                        }
                    },

                    comeDate.year,
                    // LocalDate의 monthValue는 1부터 시작하므로 -1 필요
                    comeDate.monthValue - 1,
                    comeDate.dayOfMonth
                )

//      선택 가능 범위 지정
                val minDateMillis =
                    goDate.atStartOfDay().toEpochSecond(java.time.ZoneOffset.UTC) * 1000
                val maxDateMillis =
                    goDate.plusMonths(6).atStartOfDay()
                        .toEpochSecond(java.time.ZoneOffset.UTC) * 1000

                datePickerDialog.datePicker.minDate = minDateMillis
                datePickerDialog.datePicker.maxDate = maxDateMillis

                datePickerDialog.show()
            }
        }

        //  알람 창 띄우는 함수
        private fun showAlert(message: String) {
            AlertDialog.Builder(this)
                .setTitle("Flight Log")
                .setMessage(message)
                .setPositiveButton("확인") { dialog, _ -> dialog.dismiss() }
                .show()
        }

        //  인원 선택 창으로 감
        fun chooseSelectPeople() {
            binding.choosePeopleText.setOnClickListener {
                val dialog = SelectPeopleDialogFragment()
                dialog.show(supportFragmentManager, "SelectPeopleDialog")
            }
        }

        //  총 인원 수 수정해주는 함수
        override fun onPassengerSelected(result: String) {
            binding.choosePeopleText.text = result
        }

        //  출발지 설정
        fun chooseDeparture() {
            binding.departureText.setOnClickListener {
                val dialog = ChooseDestinationFragment()
                dialog.show(supportFragmentManager, "ChooseDestinationFragment")
            }
        }

        //  도착지 설정
        fun chooseDestination() {
            binding.destinationText.setOnClickListener {

            }


//사이드바

//    툴바, 메뉴가 보이게 활성화
//    val navView : NavigationView = findViewById(R.id.nav_view)
            val toolbar = binding.toolBar.toolBarActivity
            val menuButton = binding.toolBar.flightLogMenu

//    드로어 레이아웃
            val drawerLayout = binding.main

//     툴바를 액션바로 설정. 제어권 획득.
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false) // 제목 제거 (선택)

//     버튼 클릭 이벤트 활성화
            menuButton.setOnClickListener {
                drawerLayout.openDrawer(GravityCompat.START)
            }



            // 햄버거 버튼 클릭으로 드로어 닫음
            drawerLayout.closeDrawer(GravityCompat.START)
            true
//
//    // 메뉴 클릭 이벤트 처리(사용하지 않음)
//    navView.setNavigationItemSelectedListener { menuItem ->
//      when (menuItem.itemId) {
//        R.id.nav_myinfo -> {
//        }
//        R.id.nav_reservations -> {
//        }
//        R.id.nav_ticket_info -> {
//        }
//        R.id.nav_ticket_info -> {
//        }
//        R.id.nav_pay_list -> {
//        }
//      }
        }
    }
