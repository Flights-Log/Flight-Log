package bitc.fullstack.FlightLog

import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.FlightLog.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  //  ActivityMainBinding
  private val binding: ActivityMainBinding by lazy {
    ActivityMainBinding.inflate(layoutInflater)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(binding.root)

//    툴바 아이콘
    val menuButton: ImageButton = findViewById(R.id.flight_log_menu)
    val iconButton: ImageView = findViewById(R.id.flight_log_icon)
    val loginButton: TextView = findViewById(R.id.flight_log_login)

    ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }

//    ClickListener
    menuButton.setOnClickListener {
      Log.d("flightLog", "메뉴 버튼 누름")
    }

    iconButton.setOnClickListener {
      Log.d("flightLog", "아이콘 버튼 누름")
    }

    loginButton.setOnClickListener {
      Log.d("flightLog", "로그인 버튼 누름")
    }
  }
}