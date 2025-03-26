package bitc.fullstack.FlightLog.flightmain

import android.app.DatePickerDialog
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.FlightLog.R
import bitc.fullstack.FlightLog.databinding.ActivityJoinMemberBinding
import bitc.fullstack.FlightLog.dto.JoinDTO

class JoinMemberActivity : AppCompatActivity() {
  private val binding: ActivityJoinMemberBinding by lazy {
    ActivityJoinMemberBinding.inflate(layoutInflater)
  }

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


    val email = findViewById<EditText>(R.id.editEmail).text.toString()
    val phone = findViewById<EditText>(R.id.editPhone).text.toString()
    val location = findViewById<EditText>(R.id.editLocation).text.toString()


    val btnMale = findViewById<AppCompatButton>(R.id.btnMale)
    val btnFemale = findViewById<AppCompatButton>(R.id.btnFemale)



    val editBirthDate = findViewById<EditText>(R.id.editBirthDate)

    binding.btnMale.setOnClickListener {
      selectedGender = "M"
      btnMale.setBackgroundResource(R.drawable.gender_button_selected)
      btnFemale.setBackgroundResource(R.drawable.gender_button_unselected)
      btnMale.setTextColor(Color.WHITE)
      btnFemale.setTextColor(Color.BLACK)
    }

    binding.btnFemale.setOnClickListener {
      selectedGender = "F"
      btnFemale.setBackgroundResource(R.drawable.gender_button_selected)
      btnMale.setBackgroundResource(R.drawable.gender_button_unselected)
      btnFemale.setTextColor(Color.WHITE)
      btnMale.setTextColor(Color.BLACK)
    }

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
    fun joinMember() {
      val user = JoinDTO(
        flightUserId = binding.editId.text.toString(),
        flightUserPw = binding.editPw.text.toString(),
        flightUserFirstname = binding.editFirstName.text.toString(),
        flightUserLastname = binding.editLastName.text.toString(),
        flightUserKoFirstname = binding.editKoFirstName.text.toString(),
        flightUserKoLastname = binding.editKoLastName.text.toString(),
        flightUserPhone = binding.editPhone.text.toString(),
        flightUserBirth = binding.editBirthDate.text.toString(),
        flightUserGender = selectedGender ?: "",
        flightUserEmail = binding.editEmail.text.toString())
    }
  }
}