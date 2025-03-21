package bitc.fullstack.FlightLog

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.FlightLog.databinding.ActivityAllViewBinding
import bitc.fullstack.FlightLog.databinding.ActivityMainBinding

class AllViewActivity : AppCompatActivity() {
  //  ActivityMainBinding
  private val binding: ActivityAllViewBinding by lazy {
    ActivityAllViewBinding.inflate(layoutInflater)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(binding.main)

    ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }

    binding.mainActivityButton.setOnClickListener {
      val intent = Intent(this, MainActivity::class.java)
      startActivity(intent)
    }

    binding.goAirplaneActivityButton.setOnClickListener {
      val intent = Intent(this, GoAirplaneActivity::class.java)
      startActivity(intent)
    }

    binding.goAirplaneChooseSeatActivityButton.setOnClickListener {
      val intent = Intent(this, GoAirplaneChooseSeatActivity::class.java)
      startActivity(intent)
    }

    binding.comeAirplaneActivityButton.setOnClickListener {
      val intent = Intent(this, ComeAirplaneActivity::class.java)
      startActivity(intent)
    }

    binding.comeAirplaneChooseSeatActivityButton.setOnClickListener {
      val intent = Intent(this, ComeAirplaneChooseSeatActivity::class.java)
      startActivity(intent)
    }

    binding.ticketHolderActivityButton.setOnClickListener {
      val intent = Intent(this, TicketHolderActivity::class.java)
      startActivity(intent)
    }

    binding.paymentHistoryActivityButton.setOnClickListener {
      val intent = Intent(this, PaymentHistoryActivity::class.java)
      startActivity(intent)
    }

    binding.reservationConfirmActivityButton.setOnClickListener {
      val intent = Intent(this, ReservationConfirmActivity::class.java)
      startActivity(intent)
    }

    binding.selectPeopleActivityButton.setOnClickListener {
      val intent = Intent(this, SelectPeopleActivity::class.java)
      startActivity(intent)
    }

    binding.joinMemberActivityButton.setOnClickListener {
      val intent = Intent(this, JoinMemberActivity::class.java)
      startActivity(intent)
    }


  }
}