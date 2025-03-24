package bitc.fullstack.FlightLog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import bitc.fullstack.FlightLog.databinding.ActivityMainBinding
import bitc.fullstack.FlightLog.databinding.FragmentSelectPeopleDialogBinding

class SelectPeopleDialogFragment : DialogFragment() {
  private val binding: FragmentSelectPeopleDialogBinding by lazy {
    FragmentSelectPeopleDialogBinding.inflate(layoutInflater)
  }

  //  성인, 소아, 유아 수를 저장하는 변수
  private var adultCount = 1
  private var childCount = 0
  private var babyCount = 0
  private var allPeopleCount = 1

  @SuppressLint("MissingInflatedId", "SetTextI18n")
  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val builder = android.app.AlertDialog.Builder(requireContext())
    val inflater = requireActivity().layoutInflater
    val view = inflater.inflate(R.layout.fragment_select_people_dialog, null)

//    UI 가져오기
//    어른
    val adultMinusButton: AppCompatButton = view.findViewById(R.id.adult_minus_button)
    val adultPlusButton: AppCompatButton = view.findViewById(R.id.adult_plus_button)
    val adultCountText: TextView = view.findViewById(R.id.adult_count_text)

//    소아
    val childMinusButton: AppCompatButton = view.findViewById(R.id.child_minus_button)
    val childPlusButton: AppCompatButton = view.findViewById(R.id.child_plus_button)
    val childCountText: TextView = view.findViewById(R.id.child_count_text)

//    유아
    val babyMinusButton: AppCompatButton = view.findViewById(R.id.baby_minus_button)
    val babyPlusButton: AppCompatButton = view.findViewById(R.id.baby_plus_button)
    val babyCountText: TextView = view.findViewById(R.id.baby_count_text)

//    총 인원
    val allPeopleCountText: TextView = view.findViewById(R.id.all_people_count_text)

//    어른, 소아, 유아 초기값
    adultCountText.text = adultCount.toString()
    childCountText.text = childCount.toString()
    babyCountText.text = babyCount.toString()
    updateTotalPeopleCount(allPeopleCountText)

//    어른 버튼 클릭
    adultMinusButton.setOnClickListener {
      if (adultCount > 1) {
        adultCount--
        adultCountText.text = adultCount.toString()
        updateTotalPeopleCount(allPeopleCountText)
      }
    }
    adultPlusButton.setOnClickListener {
      if (allPeopleCount < 10) {
        adultCount++
        updateTotalPeopleCount(allPeopleCountText)
        adultCountText.text = adultCount.toString()
      } else {
        showAlert("총 인원이 10명을 넘을 수는 없습니다")
        Log.d("flightLog", "총 인원 $allPeopleCount 이 10명 이상입니다")
      }
    }

//    소아 버튼 클릭
    childMinusButton.setOnClickListener {
      if (childCount > 0) {
        childCount--
        childCountText.text = childCount.toString()
        updateTotalPeopleCount(allPeopleCountText)
      }
    }
    childPlusButton.setOnClickListener {
      if (allPeopleCount < 10) {
        childCount++
        updateTotalPeopleCount(allPeopleCountText)
        childCountText.text = childCount.toString()
      } else {
        showAlert("총 인원이 10명을 넘을 수는 없습니다")
        Log.d("flightLog", "총 인원 $allPeopleCount 이 10명 이상입니다")
      }
    }

//    유아 버튼 클릭
    babyMinusButton.setOnClickListener {
      if (babyCount > 0) {
        babyCount--
        babyCountText.text = babyCount.toString()
        updateTotalPeopleCount(allPeopleCountText)
      }
    }
    babyPlusButton.setOnClickListener {
      if (allPeopleCount < 10) {
        babyCount++
        updateTotalPeopleCount(allPeopleCountText)
        babyCountText.text = babyCount.toString()
      } else {
        showAlert("총 인원이 10명을 넘을 수는 없습니다")
        Log.d("flightLog", "총 인원 $allPeopleCount 이 10명 이상입니다")
      }
    }

//    총 인원 수 출력
    builder.setView(view)
      .setTitle("인원 선택 (최소 1 명 ~ 최대 10 명)")
      .setPositiveButton("확인") { _, _ ->
        val result = "총 $allPeopleCount 명"
        (activity as? OnPassengerSelectedListener)?.onPassengerSelected(result)
      }
      .setNegativeButton("취소", null)

    return builder.create()
  }

  //  총 인원 수 갱신해주는 함수
  @SuppressLint("SetTextI18n")
  private fun updateTotalPeopleCount(allPeopleCountText: TextView) {
    allPeopleCount = adultCount + childCount + babyCount
    allPeopleCountText.text = "$allPeopleCount 명"
  }

  //  result 값을 Main 으로 옮기기 위해 만든 인터페이스
  interface OnPassengerSelectedListener {
    fun onPassengerSelected(result: String)
  }

  //  알람 창 띄우는 함수
  private fun showAlert(message: String) {
    AlertDialog.Builder(requireContext())
      .setTitle("Flight Log")
      .setMessage(message)
      .setPositiveButton("확인") { dialog, _ -> dialog.dismiss() }
      .show()
  }
}