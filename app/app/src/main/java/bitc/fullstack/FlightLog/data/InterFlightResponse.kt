package bitc.fullstack.FlightLog.data

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "response", strict = false)
data class InterFlightResponse(

  @field:Element(name = "body")
  var interflightbody: InterFlightBody? = null
)













