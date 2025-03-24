package bitc.fullstack.FlightLog.data

import com.google.gson.annotations.SerializedName
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
data class FlightItem(

  // 항공사 명
  @field:Element(name = "airlineKorean", required = false)
  var airlineKorean: String? = null,

  // 도착 지역명
  @field:Element(name = "arrivalcity", required = false)
  var arrivalcity: String? = null,

  // 도착 시간
  @field:Element(name = "domesticArrivalTime", required = false)
  var domesticArrivalTime: String? = null,

  // 운항 종료일
  @field:Element(name = "domesticEddate", required = false)
  var domesticEddate: String? = null,

  // 월요일 일정
  @field:Element(name = "domesticMon", required = false)
  var domesticMon: String? = null,

  // 화요일 일정
  @field:Element(name = "domesticTue", required = false)
  var domesticTue: String? = null,

  // 수요일 일정
  @field:Element(name = "domesticWed", required = false)
  var domesticWed: String? = null,

  // 목요일 일정
  @field:Element(name = "domesticThu", required = false)
  var domesticThu: String? = null,

  // 금요일 일정
  @field:Element(name = "domesticFri", required = false)
  var domesticFri: String? = null,

  // 토요일 일정
  @field:Element(name = "domesticSat", required = false)
  var domesticSat: String? = null,

  // 일요일 일정
  @field:Element(name = "domesticSun", required = false)
  var domesticSun: String? = null,

  // 항공편명
  @field:Element(name = "domesticNum", required = false)
  var domesticNum: String? = null,

  // 운항 시작일
  @field:Element(name = "domesticStdate", required = false)
  var domesticStdate: String? = null,

  // 출발 시간
  @field:Element(name = "domesticStartTime", required = false)
  var domesticStartTime: String? = null,

  // 시작 지역명
  @field:Element(name = "startcity", required = false)
  var startcity: String? = null,

  // ------- 저장하지 않는 데이터

  // 항공사 영어명
  @field:Element(name = "airlineEnglish", required = false)
  var airlineEnglish: String? = null,

  // 도착 항공사 코드
  @field:Element(name = "arrivalcityCode", required = false)
  var arrivalcityCode: String? = null,

  // 비행 목적 - 여객기
  @field:Element(name = "flightPurpose", required = false)
  var flightPurpose: String? = null,

  // 출발 항공 코드
  @field:Element(name = "startcityCode", required = false)
  var startcityCode: String? = null
)


