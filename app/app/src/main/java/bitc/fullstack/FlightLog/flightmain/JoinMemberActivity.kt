package bitc.fullstack.FlightLog.flightmain

import android.app.DatePickerDialog
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.FlightLog.R
import bitc.fullstack.FlightLog.appserver.AppServerClass
import bitc.fullstack.FlightLog.databinding.ActivityJoinMemberBinding
import bitc.fullstack.FlightLog.dto.JoinDTO
import bitc.fullstack.FlightLog.dto.iFlightDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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


      val api = AppServerClass.instance
      val call = api.joinMember(joinData)
      retrofitResponse(call)

    }
  }



  private fun retrofitResponse(call: Call<String>) {

    call.enqueue(object : Callback<String>{
      override fun onResponse(p0: Call<String>, res: Response<String>) {
        if (res.isSuccessful) {
          val result = res.body()
          Log.d("csy", "result : $result")
        }
        else {
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