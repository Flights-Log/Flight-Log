package bitc.fullstack.FlightLog.data

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "response", strict = false)
data class FlightResponse(
  @field:Element(name = "body")
  var flightbody: FlightBody? = null
)













