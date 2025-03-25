package bitc.fullstack.FlightLog.flightmain

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import bitc.fullstack.FlightLog.R
import bitc.fullstack.FlightLog.databinding.FragmentSelectPeopleDialogBinding

class SelectPeopleDialogFragment : DialogFragment() {
  private val binding: FragmentSelectPeopleDialogBinding by lazy {
    FragmentSelectPeopleDialogBinding.inflate(layoutInflater)
  }

  //  인원수 저장 변수
  private var adultCount = 1

  @SuppressLint("MissingInflatedId", "SetTextI18n")
  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val builder = AlertDialog.Builder(requireContext())
    // 다이얼로그에 뷰 설정
    builder.setView(binding.root)
      .setTitle("인원 선택")
      .setPositiveButton("확인", null)
      .setNegativeButton("취소", null)


//    인원수 초기값
    binding.adultCountText.text = adultCount.toString()
    updateTotalPeopleCount(adultCount)

//   - 버튼 클릭
    binding.adultMinusButton.setOnClickListener {
      if (adultCount > 1) {
        adultCount--
        Log.d("flightLog", "adultCount : $adultCount")
        binding.adultCountText.text = adultCount.toString()
        updateTotalPeopleCount(adultCount)
      }
    }

//    + 버튼 클릭
    binding.adultPlusButton.setOnClickListener {
      if (adultCount < 10) {
        adultCount++
        updateTotalPeopleCount(adultCount)
        Log.d("flightLog", "adultCount : $adultCount")

        binding.adultCountText.text = adultCount.toString()
      } else {
        showAlert("총 인원이 10명을 넘을 수는 없습니다")
        Log.d("flightLog", "총 인원 $adultCount 이 10명 이상입니다")
      }
    }
    return builder.create()
  }

  //  총 인원 수 갱신해주는 함수
  @SuppressLint("SetTextI18n")
  private fun updateTotalPeopleCount(adultCount: Int) {
    binding.allPeopleCountText.text = "$adultCount 명"
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