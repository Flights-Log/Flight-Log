package bitc.fullstack.FlightLog.dto

data class TicketHolderDTO(
    val flightPassport: String,          // 여권 번호
    val flightReno : String,             // 예약 번호
    val flightId: Int,                   // 비행기 ID
    val flightStartId : Int,
    val flightArrId : Int,
    val flightUserId: String,            // 티켓팅한 ID
    val flightMemFirstName:String,       // 이름
    val flightMemLastName:String,        // 성
    val flightReserveSDate: String,      // 출발 날짜
    val flightInfoAirline: String,       // 항공사 이름
    val flightInfoStartCity: String,     // 출발 도시
    val flightInfoStartTime: String,     // 출발 시간
    val flightInfoArrivalCity: String,   // 도착 도시
    val flightInfoArrivalTime: String,   // 도착 시간
    val flightMemStartSeatNum : String,  // 가는 좌석
    val flightMemArriveSeatNum : String, // 오는 좌석
    val selectedSeat : String,           // 선택된 좌석 (가는편 오는편 판단 결과 좌석)
    val flightMemStartPrice : Int,       // 가는 가격
    val flightMemArrPrice : Int,         // 오는 가격
    val selectedPrice : Int,             // 선택된 가격
    val flightMemLuggage: String,        // 수하물 정보
    val flightDistance:  Double,         // 비행 거리(가격 계산용)
    val flightArrPayCheck: String,       // 오는 날 결제 여부
    val flightStartPayCheck: String,     // 가는 날 결제 여부
    var isChecked: Boolean = false,      // 티켓 체크 상태 확인
    var paymentStatus: String? = null,    // 결제 상태 (예: '완료', '진행 중')
    val flightIds: List<Int> = listOf()  // 여러 개의 flightId를 담을 리스트 추가

)
