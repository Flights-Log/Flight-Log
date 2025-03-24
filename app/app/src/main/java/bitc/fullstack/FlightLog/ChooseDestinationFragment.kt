package bitc.fullstack.FlightLog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.DialogFragment
import bitc.fullstack.FlightLog.databinding.ActivityMainBinding
import bitc.fullstack.FlightLog.databinding.FragmentChooseDestinationBinding
import bitc.fullstack.FlightLog.databinding.FragmentSelectPeopleDialogBinding

class ChooseDestinationFragment : DialogFragment() {
  private val binding: FragmentChooseDestinationBinding by lazy {
    FragmentChooseDestinationBinding.inflate(layoutInflater)
  }

  @SuppressLint("MissingInflatedId", "SetTextI18n")
  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val builder = AlertDialog.Builder(requireContext())
    val inflater = requireActivity().layoutInflater
    val view = inflater.inflate(R.layout.fragment_choose_destination, null)

    // 다이얼로그에 뷰 설정
    builder.setView(view)
      .setTitle("출발지 선택")
      .setPositiveButton("확인") { dialog, _ -> dialog.dismiss() }
      .setNegativeButton("취소", null)

    return builder.create()
  }

  //  서치뷰
  private fun initSearchView() {
    binding.destinationSearchView.isSubmitButtonEnabled = true
    binding.destinationSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        return false
      }

      override fun onQueryTextChange(newText: String?): Boolean {
        return false
      }
    })
  }
}