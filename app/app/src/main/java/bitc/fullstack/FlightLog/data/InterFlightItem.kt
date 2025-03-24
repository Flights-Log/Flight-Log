package bitc.fullstack.FlightLog.data

import com.google.gson.annotations.SerializedName
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
data class InterFlightItem(

  // 국제 항공표 -> 도착 시간 X

  // 항공사 명
  @field:Element(name = "airlineKorean", required = false)
  var airlineKorean: String? = null,

  // 도착 지역명
  @field:Element(name = "arrvcity", required = false)
  var arrvcity: String? = null,

  // 운항 종료일
  @field:Element(name = "internationalEddate", required = false)
  var internationalEddate: String? = null,

  // 월요일 일정
  @field:Element(name = "internationalMon", required = false)
  var internationalMon: String? = null,

  // 화요일 일정
  @field:Element(name = "internationalTue", required = false)
  var internationalTue: String? = null,

  // 수요일 일정
  @field:Element(name = "internationalWed", required = false)
  var internationalWed: String? = null,

  // 목요일 일정
  @field:Element(name = "internationalThu", required = false)
  var internationalThu: String? = null,

  // 금요일 일정
  @field:Element(name = "internationalFri", required = false)
  var internationalFri: String? = null,

  // 토요일 일정
  @field:Element(name = "internationalSat", required = false)
  var internationalSat: String? = null,

  // 일요일 일정
  @field:Element(name = "internationalSun", required = false)
  var internationalSun: String? = null,

  // 항공편명
  @field:Element(name = "internationalNum", required = false)
  var internationalNum: String? = null,

  // 운항 시작일
  @field:Element(name = "internationalStdate", required = false)
  var internationalStdate: String? = null,

  // 출발 시간
  @field:Element(name = "internationalTime", required = false)
  var internationalTime: String? = null,

  // 시작 지역명
  @field:Element(name = "deptcity", required = false)
  var deptcity: String? = null,

  // ------- 저장하지 않는 데이터

  // 항공사 영어명
  @field:Element(name = "airlineEnglish", required = false)
  var airlineEnglish: String? = null,

  // 도착 항공사 코드
  @field:Element(name = "arrvcitycode", required = false)
  var arrvcitycode: String? = null,

  // 비행 목적 - 여객기
  @field:Element(name = "flightPurpose", required = false)
  var flightPurpose: String? = null,

  // 출발 항공 코드
  @field:Element(name = "deptcitycode", required = false)
  var deptcitycode: String? = null
)


