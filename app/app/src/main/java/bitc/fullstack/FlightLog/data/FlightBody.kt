package bitc.fullstack.FlightLog.data

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "body", strict = false)
data class FlightBody(
  // 국내 운항 정보 리스트
  @field:ElementList(name = "items", inline = false, required = false)
  var flightItems: List<FlightItem> = mutableListOf(),

)












