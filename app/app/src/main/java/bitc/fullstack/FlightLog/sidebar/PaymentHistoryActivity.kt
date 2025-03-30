package bitc.fullstack.FlightLog.sidebar

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.FlightLog.databinding.ActivityPaymentHistoryBinding
import bitc.fullstack.FlightLog.flightmain.MainActivity

class PaymentHistoryActivity : MainActivity() {
  //  ActivityMainBinding
  private val binding: ActivityPaymentHistoryBinding by lazy {
    ActivityPaymentHistoryBinding.inflate(layoutInflater)
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
    sidebarInfo()
    //사이드바

    // View 바인딩
//    val navView : NavigationView = findViewById(R.id.nav_view)
    val toolbar = binding.toolBar.toolBarActivity
    val menuButton = binding.toolBar.flightLogMenu

//    드로어 레이아웃
    val drawerLayout = binding.root

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
    }
  }