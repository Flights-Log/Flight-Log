package bitc.fullstack.FlightLog.flightchoose

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.FlightLog.R
import bitc.fullstack.FlightLog.databinding.ActivityPassengerBinding

class PassengerActivity : AppCompatActivity() {
    private val binding: ActivityPassengerBinding by lazy {
        ActivityPassengerBinding.inflate(layoutInflater)
    }

    private lateinit var dateEditText: EditText // 날짜를 표시할 edittext
    private lateinit var dateButton: Button     // 날짜 선택 버튼

    private lateinit var addPassengerButton: Button
    private lateinit var saveButton: Button
    private lateinit var passengerDynamicLayout: LinearLayout
    private var passengerViews = mutableListOf<LinearLayout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//    edittext랑 버튼 연결
        dateEditText = findViewById(R.id.et_date)
        dateButton = findViewById(R.id.btn_birth)

//        버튼 및 레이아웃 초기화
        addPassengerButton = findViewById(R.id.btn_add_passenger)
        saveButton = findViewById(R.id.passenger_info_save)
        passengerDynamicLayout = findViewById(R.id.passenger_add_layout)


        initEventListener()
    }

    private fun initEventListener() {

//    신분증 상 성별 선택 드롭 다운
        val spinnerG: Spinner = findViewById(R.id.passenger_info_gender)
        val gender = arrayOf("신분증 상 성별 선택", "여성", "남성")
        val adapterGender = ArrayAdapter(this, R.layout.spinner_center, gender)
        spinnerG.adapter = adapterGender

//    국적 선택 드롭 다운?
        val spinner: Spinner = findViewById(R.id.nationality)
        val nationalities = arrayOf("대한민국", "미국", "중국", "태국", "베트남")
        val adapter = ArrayAdapter(this, R.layout.spinner_center, nationalities)
        spinner.adapter = adapter


//    생년월일 선택 버튼 이벤트
        binding.btnBirth.setOnClickListener {

//      현재 날짜 기준으로 DatePickerDialog 설정
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

//      DatePickerDialog 생성
            val datePickerDialog =
                DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDayOfMonth ->

//        날짜 선택하면 edittext에 나오게 함
                    dateEditText.setText("$selectedYear - ${selectedMonth + 1} - $selectedDayOfMonth")
                }, year, month, day)

//      다이얼로그 띄우기
            datePickerDialog.show()

        }

//        동승객 추가 버튼 이벤트
        addPassengerButton.setOnClickListener {
            addNewPassengerForm()
        }

//        저장 하기
        saveButton.setOnClickListener {
            savePassengerInfo()
        }


    }

    //    동승자 창 화면
    private fun addNewPassengerForm() {

        val newPassengerLayout =
            layoutInflater.inflate(R.layout.passenger_form, null) as LinearLayout

        val lastNameEditText = newPassengerLayout.findViewById<EditText>(R.id.last_name)
        val firstNameEditText = newPassengerLayout.findViewById<EditText>(R.id.first_name)

//    성별 선택 Spinner
        val genderSpinner = newPassengerLayout.findViewById<Spinner>(R.id.passenger_info_gender)
        val gender = arrayOf("신분증 상 성별 선택", "여성", "남성")
        val adapterGender = ArrayAdapter(this, R.layout.spinner_center, gender)
        genderSpinner.adapter = adapterGender

//    국적 선택 Spinner
        val nationalitySpinner = newPassengerLayout.findViewById<Spinner>(R.id.nationality)
        val nationalities = arrayOf("대한민국", "미국", "중국", "태국", "베트남")
        val adapter = ArrayAdapter(this, R.layout.spinner_center, nationalities)
        nationalitySpinner.adapter = adapter

        val birthDateEditText = newPassengerLayout.findViewById<EditText>(R.id.et_date)
        val dateButton = newPassengerLayout.findViewById<Button>(R.id.btn_birth)

        dateButton.setOnClickListener {

//      현재 날짜 기준으로 DatePickerDialog 설정
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

//      DatePickerDialog 생성
            val datePickerDialog =
                DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDayOfMonth ->

//        날짜 선택하면 edittext에 나오게 함
                    birthDateEditText.setText("$selectedYear - ${selectedMonth + 1} - $selectedDayOfMonth")
                }, year, month, day)

//      다이얼로그 띄우기
            datePickerDialog.show()

        }

        val passportNumberEditText = newPassengerLayout.findViewById<EditText>(R.id.passport_number)
        val phoneNumberEditText =
            newPassengerLayout.findViewById<EditText>(R.id.passenger_phone_number)
        val baggageEditText = newPassengerLayout.findViewById<EditText>(R.id.passenger_baggage)

        passengerDynamicLayout.addView(newPassengerLayout)

        passengerViews.add(newPassengerLayout)
    }

    private fun savePassengerInfo() {

        val lastNameEditText = findViewById<EditText>(R.id.last_name)
        val firstNameEditText = findViewById<EditText>(R.id.first_name)
        val genderSpinner = findViewById<Spinner>(R.id.passenger_info_gender)
        val nationalitySpinner = findViewById<Spinner>(R.id.nationality)
        val birthDateEditText = findViewById<EditText>(R.id.et_date)
        val passportNumberEditText = findViewById<EditText>(R.id.passport_number)
        val phoneNumberEditText = findViewById<EditText>(R.id.passenger_phone_number)
        val baggageEditText = findViewById<EditText>(R.id.passenger_baggage)

        // 탑승객 정보
        val lastName = lastNameEditText.text.toString()
        val firstName = firstNameEditText.text.toString()
        val gender = genderSpinner.selectedItem.toString()
        val nationality = nationalitySpinner.selectedItem.toString()
        val birthDate = birthDateEditText.text.toString()
        val passportNumber = passportNumberEditText.text.toString()
        val phoneNumber = phoneNumberEditText.text.toString()
        val baggageWeight = baggageEditText.text.toString()


        Log.d(
            "탑승객 정보",
            "$lastName $firstName, $gender, $nationality, $birthDate, $passportNumber, $phoneNumber, $baggageWeight"
        )

        passengerViews.forEachIndexed { index,  passengerLayout ->
            val lastNameEditText = passengerLayout.findViewById<EditText>(R.id.last_name)
            val firstNameEditText = passengerLayout.findViewById<EditText>(R.id.first_name)
            val genderSpinner = passengerLayout.findViewById<Spinner>(R.id.passenger_info_gender)
            val nationalitySpinner = passengerLayout.findViewById<Spinner>(R.id.nationality)
            val birthDateEditText = passengerLayout.findViewById<EditText>(R.id.et_date)
            val passportNumberEditText = passengerLayout.findViewById<EditText>(R.id.passport_number)
            val phoneNumberEditText = passengerLayout.findViewById<EditText>(R.id.passenger_phone_number)
            val baggageEditText = passengerLayout.findViewById<EditText>(R.id.passenger_baggage)


            val lastName = lastNameEditText.text.toString()
            val firstName = firstNameEditText.text.toString()
            val gender = genderSpinner.selectedItem.toString()
            val nationality = nationalitySpinner.selectedItem.toString()
            val birthDate = birthDateEditText.text.toString()
            val passportNumber = passportNumberEditText.text.toString()
            val phoneNumber = phoneNumberEditText.text.toString()
            val baggageWeight = baggageEditText.text.toString()


            Log.d(
                "동승객 정보 ${index + 1}",
                "$lastName $firstName, $gender, $nationality, $birthDate, $passportNumber, $phoneNumber, $baggageWeight"
            )
        }

//        탑승객 정보 입력란 초기화
        lastNameEditText.text.clear()
        firstNameEditText.text.clear()
        genderSpinner.setSelection(0)
        nationalitySpinner.setSelection(0)
        birthDateEditText.text.clear()
        passportNumberEditText.text.clear()
        phoneNumberEditText.text.clear()
        baggageEditText.text.clear()

//        동승객 입력란도 초기화
        passengerDynamicLayout.removeAllViews()
        passengerViews.clear()
    }
}