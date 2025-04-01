package bitc.fullstack.FlightLog.flightmain

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View.GONE
import android.view.View.VISIBLE
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import bitc.fullstack.FlightLog.R
import bitc.fullstack.FlightLog.databinding.ActivityMainBinding
import bitc.fullstack.FlightLog.flightchoose.GoAirplaneActivity
import bitc.fullstack.FlightLog.flightchoose.ReservationCheckActivity
import bitc.fullstack.FlightLog.sidebar.LoginActivity
import bitc.fullstack.FlightLog.sidebar.MyInfoActivity
import bitc.fullstack.FlightLog.sidebar.NonmemberActivity
import bitc.fullstack.FlightLog.sidebar.TicketHolderActivity
import com.google.android.material.navigation.NavigationView
import java.time.LocalDate
import java.util.Calendar
import kotlin.math.abs
import kotlin.math.round

class MainActivity : AppCompatActivity(),
  SelectPeopleDialogFragment.OnPassengerSelectedListener,
  OnDepartureSelectedListener,
  OnArriveSelectedListener {

  //  ActivityMainBinding
  private val binding: ActivityMainBinding by lazy {
    ActivityMainBinding.inflate(layoutInflater)
  }

  //  쿠키 저장
  private val cookieManager by lazy { CookieManager.getInstance() }

  private lateinit var toggle: ActionBarDrawerToggle

  //  현재 날짜값 및 도착 예정 날짜값
  private var goDate = LocalDate.now()
  private var comeDate = LocalDate.now()

  //  출발지 및 도착지
  private var selectedDeparture: String = "출발지"
  private var selectedArrive: String = "도착지"

  //  출발지와 도착지를 바꿀 때 임시로 저장할 string 객체 하나
  private var tempLocation: String = ""

  private var selectedPeople: Int = 1

  private var roundTripChecked: Boolean = false


  //  유저 이름, 아이디 쿠키 저장용
  private lateinit var cookieUserId: String
  private lateinit var cookieUserFirstName: String
  private lateinit var cookieUserLastName: String

  //  만들어지만 할거
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(binding.root)

    ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }

    //    쿠키 체크용
    cookieManager.apply {
      this.acceptCookie()
    }


//    가는날 오는날 텍스트, 날짜 선택
    chooseGoDate()
    chooseComeDate()

//    인원 선택 창으로 가는 함수
    chooseSelectPeople()

//    출발지 설정
    chooseDeparture()

//    도착지 설정
    chooseArrive()

//    출발지와 도착지를 바꾸기
    changeDestinationArrive()

//    조회하기 버튼
    searchFlight()

//    왕복, 편도 여부 묻는거
    oneWayOrRoundTrip()

    //    캐러샐뷰
    carrouselView()

  }
  // 새로고침
  override fun onResume() {
    super.onResume()
    //    값 받아오기
    getCookieExtra()
    navViewFunc()
  }
  override fun onDestroy() {
//    쿠키 삭제용
    super.onDestroy()
    val callback = ValueCallback<Boolean> {
      println(if (it) "success to remove" else "fail to remove")
    }
    cookieManager.removeAllCookies(callback)
  }

  //  점 세개 메뉴 버튼 안보이게
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    return false
  }

  //  값 받아오기
  @SuppressLint("SetTextI18n", "RestrictedApi")
  fun getCookieExtra() {
//    쿠키 있는지 확인용
    hasCookies()
    Log.d("flightLog", "쿠키 유무 : ${hasCookies()}")

    cookieUserId = getCookie("userId").toString()
    Log.d("flightLog", "cookieUserId : $cookieUserId")

    cookieUserFirstName = getCookie("userFirstName").toString()
    Log.d("flightLog", "cookieUserFirstName : $cookieUserFirstName")

    cookieUserLastName = getCookie("userLastName").toString()
    Log.d("flightLog", "cookieUserLastName : $cookieUserLastName")
  }

  //  쿠키 있는지 확인용
  private fun hasCookies(): Boolean {
    return cookieManager.hasCookies()
  }

  //  쿠키에서 Id 값 가져오기
  private fun getCookie(key: String?): String? {
    return cookieManager.getCookie(key)
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
      isLogin?.text = "$cookieUserFirstName$cookieUserLastName 님 환영합니다"
      loginButton?.text = "로그아웃"

      myInfoItem.isVisible = true
      ticketHolderItem.isVisible = true
      showReservationItem.isVisible = true
      loginButton?.setOnClickListener {
        Toast.makeText(this@MainActivity, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()

//        쿠키 삭제
        val callback = ValueCallback<Boolean> {
          println(if (it) "logout" else "fail to logout")
        }
        cookieManager.removeAllCookies(callback)

//        메인 액티비티 재시작
        val intent = Intent(this@MainActivity, MainActivity::class.java)
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
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
      }
    }

    //    메뉴 어디를 누르는지에 따라서 가는 곳이 달라짐
    navView.setNavigationItemSelectedListener { menuItem ->
      when (menuItem.itemId) {
//        마이페이지
        R.id.nav_user_info -> {
          val intent = Intent(this@MainActivity, MyInfoActivity::class.java)
          intent.putExtra("userId",cookieUserId)
          startActivity(intent)
        }

//        티켓 홀더
        R.id.nav_ticket_holder -> {
          val intent = Intent(this@MainActivity, TicketHolderActivity::class.java)
          startActivity(intent)
        }

//        예약 조회
        R.id.nav_show_reservation -> {
          val intent = Intent(this@MainActivity, NonmemberActivity::class.java)
          startActivity(intent)
        }

//        메인 화면으로
        R.id.nav_go_to_main -> {
          val intent = Intent(this@MainActivity, MainActivity::class.java)
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








    // 캐러셀 뷰
  fun carrouselView(){
    //    캐러샐뷰
    val imageList =
      listOf(
        R.drawable.jeju_island_promotion,
        R.drawable.osaka_gansai_promotion,
        R.drawable.beijing_promotion,
        R.drawable.quindao_promotion
      )

    val viewPager: ViewPager2 = findViewById(R.id.viewPager)
    viewPager.adapter = ImageAdapter(imageList)

//    옆 아이템이 살짝 보이게
    viewPager.offscreenPageLimit = 4
    val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
    val pageOffsetPx = resources.getDimensionPixelOffset(R.dimen.offset)

    viewPager.setPageTransformer { page, position ->
      val offset = pageOffsetPx * position
      page.translationX = -offset
      page.scaleY = 1 - (0.15f * abs(position))
      page.alpha = 1 - (0.3f * abs(position))
    }
  }


  //  가는 날 텍스트(chooseGoDateText) 관련 함수
  fun chooseGoDate() {
//    날짜 출력
    binding.chooseGoDateText.text = goDate.toString()
    Log.d("flightLog", "바꾸기 전 goDate : $goDate")

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
//          if (comeDate.isBefore(goDate.plusWeeks(1))) {
//            comeDate = goDate.plusWeeks(1)
//            binding.chooseComeDateText.text = comeDate.toString()
//          }
          binding.chooseGoDateText.text = goDate.toString()
          Log.d("flightLog", "바꾼 후 goDate : $goDate")

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
    binding.chooseComeDateText.text = comeDate.toString()
    Log.d("flightLog", "바꾸기 전 comeDate : $comeDate")

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
            Log.d("flightLog", "바꾼 후 comeDate : $comeDate")

          }
        },

        comeDate.year,
        // LocalDate의 monthValue는 1부터 시작하므로 -1 필요
        comeDate.monthValue - 1,
        comeDate.dayOfMonth
      )

//      선택 가능 범위 지정
      val minDateMillis = goDate.atStartOfDay().toEpochSecond(java.time.ZoneOffset.UTC) * 1000
      val maxDateMillis =
        goDate.plusMonths(6).atStartOfDay().toEpochSecond(java.time.ZoneOffset.UTC) * 1000

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
      dialog.setOnPassengerSelectedListener(this)
      dialog.show(supportFragmentManager, "SelectPeopleDialog")
    }
  }

  //  총 인원 수 수정해주는 함수
  override fun onPassengerSelected(result: String) {
    binding.choosePeopleText.text = result
    Log.d("flightLog", "result : $result")
//    n 명 형식의 데이터를 공백을 기준으로 자르고, 그 0번째 값을 가져와서 숫자로 만든다
    selectedPeople = result.split(" ")[0].toInt()
    Log.d("flightLog", "selectedPeople = $selectedPeople")
  }

  //  출발지 설정
  fun chooseDeparture() {
    binding.departureText.setOnClickListener {
      val dialog = ChooseDepartureFragment()
      dialog.show(supportFragmentManager, "ChooseDepartureFragment")
    }
  }

  //  도착지 설정
  fun chooseArrive() {
    binding.arriveText.setOnClickListener {
      if (selectedDeparture == "") {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("출발지를 먼저 선택해주세요")
        builder.setPositiveButton("확인", null)
        builder.create()
        builder.show()
      } else {
        val dialog = ChooseArriveFragment()
        dialog.setSelectedDeparture(selectedDeparture)
        dialog.show(supportFragmentManager, "ChooseArriveFragment")
      }
    }
  }

  //  ChooseDepartureFragment 에서 가져온 출발지
//  MainActivity 의 출발지에 텍스트로 넣기
  override fun onDepartureSelected(departure: String) {
    binding.departureText.text = departure
    selectedDeparture = departure
    Log.d("flightLog", "출발지 : $departure")
  }

  //  ChooseArriveFragment 에서 가져온 출발지
//  MainActivity 의 도착지에 텍스트로 넣기
  override fun onArriveSelected(arrive: String) {
    binding.arriveText.text = arrive
    selectedArrive = arrive
    Log.d("flightLog", "도착지 : $arrive")
  }

  //  출발지와 도착지 바꾸기
  fun changeDestinationArrive() {
    binding.changeDestinationArriveArrow.setOnClickListener {
      tempLocation = selectedDeparture
      selectedDeparture = selectedArrive
      selectedArrive = tempLocation

      binding.departureText.text = selectedDeparture
      binding.arriveText.text = selectedArrive
    }
  }

  //  조회하기
  fun searchFlight() {
//    지금까지 선택한 출발지, 도착지, 출발일, 도착일이 GoAirplaneActivity 로 넘어감
    binding.flightSearch.setOnClickListener {
//      출발지와 도착지를 둘 다 선택하지 않은 경우
      if (selectedDeparture == "" && selectedArrive == "") {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("출발지와 도착지를 선택해주세요")
        builder.setPositiveButton("확인", null)
        builder.create()
        builder.show()
//        출발지 혹은 도착지를 선택하지 않은 경우
      } else if (selectedDeparture == "" || selectedArrive == "") {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("출발지 혹은 도착지를 선택해주세요")
        builder.setPositiveButton("확인", null)
        builder.create()
        builder.show()
      } else {
        val intent = Intent(this, GoAirplaneActivity::class.java)
        intent.putExtra("출발지", selectedDeparture)
        intent.putExtra("도착지", selectedArrive)
        intent.putExtra("출발일", goDate.toString())
        intent.putExtra("도착일", comeDate.toString())
        intent.putExtra("인원수", selectedPeople)
        intent.putExtra("왕복 선택 여부", roundTripChecked)
        startActivity(intent)
      }
    }
  }

  //  왕복과 편도 여부 묻는거
  fun oneWayOrRoundTrip() {
    binding.roundTripCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
      Log.d("flightLog", "checked : $isChecked")
//      왕복 선택 여부를 roundTripChecked 에 넣음
      roundTripChecked = isChecked

//      왕복을 선택하면 왕복 화살표와 오는 날 선택창 나오게
      if (isChecked == true) {
        binding.mainOneWayTripArrow.visibility = GONE
        binding.mainRoundTripArrow.visibility = VISIBLE
        binding.comeDateChooseLayout.visibility = VISIBLE
//        아니면 사라지게
      } else {
        binding.mainOneWayTripArrow.visibility = VISIBLE
        binding.mainRoundTripArrow.visibility = GONE
        binding.comeDateChooseLayout.visibility = GONE
      }

    }
  }
}