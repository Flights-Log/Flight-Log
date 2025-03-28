package bitc.fullstack.FlightLog.flightmain


//java/bitc/fullstack/FlightLog/flightmain/JoinMemberActivity.kt

//회원가입 액티비티


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



//  송수신 점검 코드

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