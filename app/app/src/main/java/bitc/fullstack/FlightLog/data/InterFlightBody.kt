package bitc.fullstack.FlightLog.data

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "body", strict = false)
data class InterFlightBody(
  // 국제 운항 정보 리스트
  @field:ElementList(name = "items", inline = false, required = false)
  var InterflightItems: List<InterFlightItem> = mutableListOf()
)












