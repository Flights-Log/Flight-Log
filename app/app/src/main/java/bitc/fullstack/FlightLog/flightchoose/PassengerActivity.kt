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

//        생년월일 선택 드롭다운 Spinner
        val yearSpinner = findViewById<Spinner>(R.id.year_spinner)
        val monthSpinner = findViewById<Spinner>(R.id.month_spinner)
        val daySpinner = findViewById<Spinner>(R.id.day_spinner)

        val years = (1900 .. 2100).toList()
        val yearAdapter = ArrayAdapter(this, R.layout.spinner_center,years)
        yearSpinner.adapter = yearAdapter

        val months = (1..12).toList()
        val monthAdapter = ArrayAdapter(this,R.layout.spinner_center,months)
        monthSpinner.adapter = monthAdapter

        val days = (1..31).toList()
        val daAdapter = ArrayAdapter(this, R.layout.spinner_center,days)
        daySpinner.adapter = daAdapter

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

//        생년월일 선택 드롭다운 Spinner
        val yearSpinner = newPassengerLayout.findViewById<Spinner>(R.id.year_spinner)
        val monthSpinner = newPassengerLayout.findViewById<Spinner>(R.id.month_spinner)
        val daySpinner = newPassengerLayout.findViewById<Spinner>(R.id.day_spinner)

        val years = (1900 .. 2100).toList()
        val yearAdapter = ArrayAdapter(this, R.layout.spinner_center,years)
        yearSpinner.adapter = yearAdapter

        val months = (1..12).toList()
        val monthAdapter = ArrayAdapter(this,R.layout.spinner_center,months)
        monthSpinner.adapter = monthAdapter

        val days = (1..31).toList()
        val daAdapter = ArrayAdapter(this, R.layout.spinner_center,days)
        daySpinner.adapter = daAdapter

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

        val yearSpinner = findViewById<Spinner>(R.id.year_spinner)
        val monthSpinner = findViewById<Spinner>(R.id.month_spinner)
        val daySpinner = findViewById<Spinner>(R.id.day_spinner)

        val passportNumberEditText = findViewById<EditText>(R.id.passport_number)
        val phoneNumberEditText = findViewById<EditText>(R.id.passenger_phone_number)
        val baggageEditText = findViewById<EditText>(R.id.passenger_baggage)

        // 탑승객 정보
        val lastName = lastNameEditText.text.toString()
        val firstName = firstNameEditText.text.toString()
        val gender = genderSpinner.selectedItem.toString()
        val nationality = nationalitySpinner.selectedItem.toString()
        val year = yearSpinner.selectedItem.toString()
        val month = monthSpinner.selectedItem.toString().padStart(2,'0')
        val day = daySpinner.selectedItem.toString().padStart(2,'0')
        val birthDate = "$year = $month - $day"
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
            val yearSpinner = passengerLayout.findViewById<Spinner>(R.id.year_spinner)
            val monthSpinner = passengerLayout.findViewById<Spinner>(R.id.month_spinner)
            val daySpinner = passengerLayout.findViewById<Spinner>(R.id.day_spinner)
            val passportNumberEditText = passengerLayout.findViewById<EditText>(R.id.passport_number)
            val phoneNumberEditText = passengerLayout.findViewById<EditText>(R.id.passenger_phone_number)
            val baggageEditText = passengerLayout.findViewById<EditText>(R.id.passenger_baggage)


            val lastName = lastNameEditText.text.toString()
            val firstName = firstNameEditText.text.toString()
            val gender = genderSpinner.selectedItem.toString()
            val nationality = nationalitySpinner.selectedItem.toString()
            val year = yearSpinner.selectedItem.toString()
            val month = monthSpinner.selectedItem.toString().padStart(2,'0')
            val day = daySpinner.selectedItem.toString().padStart(2,'0')
            val birthDate = "$year = $month - $day"
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
        yearSpinner.setSelection(0)
        monthSpinner.setSelection(0)
        daySpinner.setSelection(0)
        passportNumberEditText.text.clear()
        phoneNumberEditText.text.clear()
        baggageEditText.text.clear()

//        동승객 입력란도 초기화
        passengerDynamicLayout.removeAllViews()
        passengerViews.clear()
    }
}