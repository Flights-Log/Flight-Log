package bitc.fullstack.FlightLog.sidebar

import TicketListAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import bitc.fullstack.FlightLog.appserver.AppServerClass
import bitc.fullstack.FlightLog.appserver.AppServerInterface
import bitc.fullstack.FlightLog.databinding.ActivityTicketHolderBinding
import bitc.fullstack.FlightLog.dto.TicketHolderDTO
import bitc.fullstack.FlightLog.flightchoose.ReservationCheckActivity
import kr.co.bootpay.android.Bootpay
import kr.co.bootpay.android.events.BootpayEventListener
import kr.co.bootpay.android.models.BootExtra
import kr.co.bootpay.android.models.BootItem
import kr.co.bootpay.android.models.BootUser
import kr.co.bootpay.android.models.Payload
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TicketHolderActivity : AppCompatActivity() {

  private val binding: ActivityTicketHolderBinding by lazy {
    ActivityTicketHolderBinding.inflate(layoutInflater)
  }

  private val application_id = "67dfaa781328d86e8f6520e1"  // 부트페이 애플리케이션 ID
  private lateinit var appServerInterface: AppServerInterface
  private lateinit var ticketListAdapter: TicketListAdapter
  private var ticketList: MutableList<TicketHolderDTO> = mutableListOf()

  // 결제 버튼 변수
  private lateinit var paySelectedButton: Button
  private lateinit var deleteSelectedButton: Button

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(binding.root)

    // ViewBinding을 통해 버튼을 초기화
    paySelectedButton = binding.paySelectedButton
    deleteSelectedButton = binding.deleteSelectedButton

    appServerInterface = AppServerClass.instance

    // RecyclerView 설정
    binding.ticketListContainer.layoutManager = LinearLayoutManager(this)
    ticketListAdapter = TicketListAdapter(this, ticketList)
    binding.ticketListContainer.adapter = ticketListAdapter

    // 초기 데이터 로딩
    fetchTicketList()

    binding.paySelectedButton.setOnClickListener {
      goRequest(it)  // 결제 요청 메소드 호출
    }

    // 삭제 버튼 클릭 이벤트
    deleteSelectedButton.setOnClickListener {
      deleteSelectedTickets()  // 선택된 항목 삭제
    }
  }

  // 삭제할 티켓 목록을 서버에 요청
  private fun deleteSelectedTickets() {
    val selectedTickets = ticketListAdapter.getSelectedTickets()

    if (selectedTickets.isNotEmpty()) {
      // 서버로 삭제 요청
      selectedTickets.forEach { ticket ->
        val flightReno = ticket.flightReno
        val flightMemStartSeatNum = ticket.flightMemStartSeatNum

        val call = appServerInterface.deleteTicket(flightReno, flightMemStartSeatNum)
        call.enqueue(object : Callback<String> {
          override fun onResponse(call: Call<String>, response: Response<String>) {
            if (response.isSuccessful) {
              // 삭제 성공
              Toast.makeText(this@TicketHolderActivity, "티켓이 삭제되었습니다.", Toast.LENGTH_SHORT).show()

              // 서버에서 삭제된 티켓을 리스트에서 제거
              ticketList.remove(ticket)

              // Adapter에 데이터 업데이트를 알리고 UI 갱신
              ticketListAdapter.updateTicketList(ticketList)  // 여기에서 리스트를 갱신
            } else {
              Toast.makeText(this@TicketHolderActivity, "티켓 삭제에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
          }

          override fun onFailure(call: Call<String>, t: Throwable) {
            // 네트워크 오류
            Toast.makeText(this@TicketHolderActivity, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
          }
        })
      }
    } else {
      Toast.makeText(this, "삭제할 항목을 선택해주세요.", Toast.LENGTH_SHORT).show()
    }
  }



  // Toast 메시지를 간단히 표시하는 함수
  private fun showToast(message: String) {
    Toast.makeText(this@TicketHolderActivity, message, Toast.LENGTH_SHORT).show()
  }

  private fun goRequest(view: View) {
    // 선택된 티켓들만 전달
    val selectedTickets = ticketListAdapter.getSelectedTickets()

    if (selectedTickets.isNotEmpty()) {
      startPayment(selectedTickets)
    } else {
      Toast.makeText(this, "결제할 항목을 선택해주세요.", Toast.LENGTH_SHORT).show()
    }
  }

  private fun fetchTicketList() {
    val userMemId: String = "test1234"
    val call = appServerInterface.getFlightPassengers(userMemId)

    call.enqueue(object : Callback<List<TicketHolderDTO>> {
      override fun onResponse(call: Call<List<TicketHolderDTO>>, response: Response<List<TicketHolderDTO>>) {
        if (response.isSuccessful && response.body() != null) {
          ticketList = response.body() as MutableList<TicketHolderDTO>
          ticketListAdapter.updateTicketList(ticketList)
        } else {
          Toast.makeText(this@TicketHolderActivity, "데이터를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
        }
      }

      override fun onFailure(call: Call<List<TicketHolderDTO>>, t: Throwable) {
        Toast.makeText(this@TicketHolderActivity, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
      }
    })
  }

  // 결제 요청 메소드
  private fun startPayment(selectedTickets: List<TicketHolderDTO>) {
    val totalPrice = selectedTickets.sumOf { it.selectedPrice.toDouble() } // 결제 금액 계산

    // 사용자 정보 설정
    val user = BootUser().apply {
      setUsername("JohnDoe")
      setEmail("example@example.com")
      setPhone("01012345678")
    }

    // 결제 추가 설정 (할부 설정 예시)
    val extra = BootExtra().setCardQuota("0,2,3")

    // 구매 항목 설정
    val items = selectedTickets.map {
      BootItem().apply {
        setName("항공권")  // 상품명
        setQty(1)  // 수량
        setPrice(it.selectedPrice.toDouble()) // 가격
        setId("item_${selectedTickets+1}")
      }
    }

    Bootpay.init(this)  // 'this'는 현재 Activity를 의미합니다.
      .setPayload(Payload().apply {
        setApplicationId(application_id)  // 부트페이 애플리케이션 ID 설정
        setOrderName("부트페이 결제테스트")  // 결제 상품명 설정
        setPg("이니시스")  // PG사 설정 (여기서는 '나이스페이')
        setOrderId("1234")  // 주문 ID
        setMethod("카드")  // 결제 방식 (카드)
        setPrice(totalPrice)  // 결제 금액
        setUser(user)  // 사용자 정보
        setExtra(extra)  // 추가 설정 (카드 할부)
        setItems(items)  // 결제 항목들
      })
      .setEventListener(object : BootpayEventListener {
        override fun onCancel(data: String) {
          Log.d("bootpay", "cancel: $data")
        }

        override fun onError(data: String) {
          Log.d("bootpay", "error: $data")
        }

        override fun onClose() {
          Log.d("bootpay", "close")
          Bootpay.removePaymentWindow()  // 결제 창을 제거합니다.
        }

        override fun onIssued(data: String) {
          Log.d("bootpay", "issued: $data")
        }

        // 결제 진행 없이 바로 결제 완료 처리
        override fun onConfirm(data: String): Boolean {
          Log.d("bootpay", "confirm: $data")
          // 결제 창을 띄우지 않고 바로 결제 완료로 넘어가기 위해 true 리턴
          return true  // 즉시 결제 완료 상태로 처리
        }

        override fun onDone(data: String) {
          Log.d("done", data)
          // 결제 성공 후 DB 상태 업데이트 호출
          Toast.makeText(this@TicketHolderActivity, "결제가 완료되었습니다.", Toast.LENGTH_SHORT).show()

          updatePaymentStatus(selectedTickets)

          val intent = Intent(this@TicketHolderActivity, ReservationCheckActivity::class.java)
          intent.putExtra("paymentStatus", "success") // 결제 성공 상태 전달
          val flightUserId = selectedTickets.firstOrNull()?.flightUserId ?: ""
          Log.d("flightUserId","flightUserId: ${flightUserId}")
          intent.putExtra("flightUserId",flightUserId) // flight_reno 값 추가
          startActivity(intent)


        }
      })
      .requestPayment()  // 결제 요청
  }

  // updatePaymentStatus 메소드에 selectedTickets 인자를 추가
  private fun updatePaymentStatus(selectedTickets: List<TicketHolderDTO>) {
    val flightReno = selectedTickets.firstOrNull()?.flightReno?.toString() ?: ""
    val flightPassport = selectedTickets.firstOrNull()?.flightPassport?.toString() ?: ""
    val flightIds = selectedTickets.map { it.flightId }

    // flightIds를 Map으로 변환
    val flightIdsMap = flightIds.withIndex().associate { "flightIds[${it.index}]" to it.value.toString() }
    Log.d("UpdatePaymentStatus", "flightIdsMap: $flightIdsMap")

    // 로그로 출력
    Log.d("UpdatePaymentStatus", "flightIdsMap: $flightIdsMap")

    // Retrofit 호출
    val call = appServerInterface.updatePaymentStatus(flightPassport, flightReno, flightIdsMap)

    call.enqueue(object : Callback<String> {
      override fun onResponse(call: Call<String>, response: Response<String>) {
        if (response.isSuccessful) {
          Toast.makeText(this@TicketHolderActivity, "결제 상태가 성공적으로 업데이트되었습니다.", Toast.LENGTH_SHORT).show()
        } else {
          Toast.makeText(this@TicketHolderActivity, "결제 상태 업데이트에 실패했습니다.", Toast.LENGTH_SHORT).show()
        }
      }

      override fun onFailure(call: Call<String>, t: Throwable) {
        Toast.makeText(this@TicketHolderActivity, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
      }
    })
  }



}


