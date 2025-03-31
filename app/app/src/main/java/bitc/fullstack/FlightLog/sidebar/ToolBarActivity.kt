package bitc.fullstack.FlightLog.sidebar
// 사이드바 액티비티

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import bitc.fullstack.FlightLog.R
import bitc.fullstack.FlightLog.databinding.ActivityMainBinding
import bitc.fullstack.FlightLog.databinding.ActivityToolBarBinding

class ToolBarActivity : AppCompatActivity() {
  //    뷰 바인딩
  //  ActivityMainBinding
  private val binding: ActivityToolBarBinding by lazy {
    ActivityToolBarBinding.inflate(layoutInflater)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(binding.root)

//        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

    ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
    open class MainActivity
  }
}