package bitc.fullstack.FlightLog

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.FlightLog.databinding.ActivityNonMemberBinding

class NonmemberActivity : AppCompatActivity() {
  private val binding:ActivityNonMemberBinding by lazy{
    ActivityNonMemberBinding.inflate(layoutInflater)
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

    initEventListener()
  }

  private fun initEventListener(){
    binding.btnSearch.setOnClickListener {
//      val intent = Intent(this,결과화면)
//      startActivity(intent)
    }

    binding.btnHome.setOnClickListener {
//      val intent = Intent(this, 메인화면)
//      startActivity(intent)
    }
  }
}