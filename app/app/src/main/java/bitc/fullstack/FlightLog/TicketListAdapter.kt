import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.app.AlertDialog
import android.widget.ImageButton
import bitc.fullstack.FlightLog.R
import bitc.fullstack.FlightLog.dto.TicketHolderDTO

class TicketListAdapter(
    private val context: Context,
    private var ticketList: MutableList<TicketHolderDTO>
) : RecyclerView.Adapter<TicketListAdapter.TicketViewHolder>() {

    // 체크 된 항공권 리스트
    private val selectedTickets = mutableListOf<TicketHolderDTO>()

    // 선택한 항공권 반환
    fun getSelectedTickets(): List<TicketHolderDTO> {
        return selectedTickets.toList()
    }

    // 항목 삭제 메서드
    fun removeTickets(ticketsToRemove: List<TicketHolderDTO>) {
        ticketList.removeAll(ticketsToRemove)
        selectedTickets.removeAll(ticketsToRemove) // 선택 리스트에서도 제거
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_ticket, parent, false)
        return TicketViewHolder(view)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val ticket = ticketList[position]
        holder.bind(ticket)
    }

    override fun getItemCount(): Int {
        return ticketList.size
    }

    fun updateTicketList(newTicketList: MutableList<TicketHolderDTO>) {
        ticketList.clear()
        ticketList.addAll(newTicketList)
        notifyDataSetChanged()
    }

    inner class TicketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val flightReserveSDate: TextView = itemView.findViewById(R.id.flightReserveSDate)
        private val flightMemName: TextView = itemView.findViewById(R.id.flightMemName)
        private val flightAirline: TextView = itemView.findViewById(R.id.flightAirline)
        private val flightStartInfo: TextView = itemView.findViewById(R.id.flightStartInfo)
        private val flightArrivalInfo: TextView = itemView.findViewById(R.id.flightArrivalInfo)
        private val paymentAmount: TextView = itemView.findViewById(R.id.paymentAmount)
        private val ticketCheckBox: CheckBox = itemView.findViewById(R.id.ticket_check_box)





        fun bind(ticket: TicketHolderDTO) {
            // 출발일
            flightReserveSDate.text = "출발일: ${ticket.flightReserveSDate}"
            // 탑승자 이름
            flightMemName.text = "탑승자: ${ticket.flightMemFirstName} ${ticket.flightMemLastName}"
            // 항공사
            flightAirline.text = "항공사: ${ticket.flightInfoAirline}"
            // 출발지 및 출발 시간
            flightStartInfo.text = "출발지: ${ticket.flightInfoStartCity} (출발 시간 : ${ticket.flightInfoStartTime})"
            // 도착지 및 도착 시간
            flightArrivalInfo.text = "도착지: ${ticket.flightInfoArrivalCity} (도착 시간 : ${ticket.flightInfoArrivalTime})"
            // 비행 거리
            paymentAmount.text = " 가격 : ${String.format("%,d", ticket.selectedPrice)} 원"



            // 체크박스 상태 유지
            ticketCheckBox.setOnCheckedChangeListener(null) // 기존 리스너 해제
            ticketCheckBox.isChecked = selectedTickets.contains(ticket)

            // 체크박스 상태 감지
            ticketCheckBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    if (!selectedTickets.contains(ticket)) {
                        selectedTickets.add(ticket)
                    }
                } else {
                    selectedTickets.remove(ticket)
                }
            }

            // 출발일 클릭 시 팝업 띄우기
            flightReserveSDate.setOnClickListener {
                showPopupInfo(ticket)
            }

            // 탑승자 이름 클릭 시 팝업 띄우기
            flightMemName.setOnClickListener {
                showPopupInfo(ticket)
            }

            // 출발지 및 출발 시간 클릭 시 팝업 띄우기
            flightStartInfo.setOnClickListener {
                showPopupInfo(ticket)
            }

            // 도착지 및 도착 시간 클릭 시 팝업 띄우기
            flightArrivalInfo.setOnClickListener {
                showPopupInfo(ticket)
            }

        }



        private fun showPopupInfo(ticket: TicketHolderDTO) {
            // 레이아웃 인플레이터 사용하여 XML 레이아웃을 인플레이트
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.dialog_ticket_info, null)

            // XML 레이아웃의 각 View를 찾고, ticket 데이터로 설정
            val tvDepartureDate: TextView = view.findViewById(R.id.tv_departure_date)
            val tvStartInfo: TextView = view.findViewById(R.id.tv_start_info)
            val tvArrivalInfo: TextView = view.findViewById(R.id.tv_arrival_info)
            val tvPassengerName: TextView = view.findViewById(R.id.tv_passenger_name)
            val tvPassport: TextView = view.findViewById(R.id.tv_passport)
            val tvSeatInfo: TextView = view.findViewById(R.id.tv_seat_info)
            val tvLuggage: TextView = view.findViewById(R.id.tv_luggage)
            val tvFlightReno : TextView = view.findViewById(R.id.tv_flightReno)
            val tvFlightId : TextView =view.findViewById(R.id.tv_flightId)

            // 데이터를 뷰에 설정
            tvDepartureDate.text = "출발일: ${ticket.flightReserveSDate}"
            tvStartInfo.text = "출발지 및 출발 시간: ${ticket.flightInfoStartCity} (${ticket.flightInfoStartTime})"
            tvArrivalInfo.text = "도착지 및 도착 시간: ${ticket.flightInfoArrivalCity} (${ticket.flightInfoArrivalTime})"
            tvPassengerName.text = "탑승자: ${ticket.flightMemFirstName} ${ticket.flightMemLastName}"
            tvPassport.text = "여권 번호: ${ticket.flightPassport}"
            tvSeatInfo.text = "출발 좌석 번호: ${ticket.flightMemStartSeatNum}, 도착 좌석 번호: ${ticket.flightMemArriveSeatNum}, 선택된 좌석: ${ticket.selectedSeat}"
            tvLuggage.text = "수하물 정보: ${ticket.flightMemLuggage}"
            tvFlightReno.text = "예약 번호: ${ticket.flightReno}"
            tvFlightId.text = "비행편 번호 : ${ticket.flightId}"

            // 다이얼로그 빌더 생성
            val builder = AlertDialog.Builder(context)
                .setView(view)  // XML 레이아웃을 다이얼로그에 설정
                .setCancelable(true)

            val alertDialog = builder.create()

            // "닫기" 버튼 처리
            val closeButton: ImageButton = view.findViewById(R.id.closeButton)
            closeButton.setOnClickListener {
                alertDialog.dismiss()
            }

            // 다이얼로그 표시
            alertDialog.show()
        }
    }
}