package bitc.fullstack.FlightLog.flightchoose

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bitc.fullstack.FlightLog.R
import bitc.fullstack.FlightLog.dto.FlightReservationCheckDTO

class FlightReservationAdapter(
    private val flightList: List<FlightReservationCheckDTO>
) : RecyclerView.Adapter<FlightReservationAdapter.FlightReservationViewHolder>() {


    // ViewHolder를 생성하는 부분
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightReservationViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_flight_reservation, parent, false)
        return FlightReservationViewHolder(itemView)
    }

    // ViewHolder에 데이터를 바인딩하는 부분
    override fun onBindViewHolder(holder: FlightReservationViewHolder, position: Int) {
        val flightReservation = flightList[position]
        holder.bind(flightReservation)
    }

    override fun getItemCount(): Int {
        return flightList.size
    }



    // ViewHolder 클래스 정의
    inner class FlightReservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val reservationNumber: TextView = itemView.findViewById(R.id.reservation_number)
        private val lastName: TextView = itemView.findViewById(R.id.last_name)
        private val firstName: TextView = itemView.findViewById(R.id.first_name)
        private val startCity: TextView = itemView.findViewById(R.id.start_city)
        private val arrivalCity: TextView = itemView.findViewById(R.id.arrival_city)
        private val departureDate: TextView = itemView.findViewById(R.id.departure_date)
        private val departureTime: TextView = itemView.findViewById(R.id.departure_time)
        private val arrivalTime: TextView = itemView.findViewById(R.id.arrival_time)
        private val passengers: TextView = itemView.findViewById(R.id.passengers)
        private val passportNumber: TextView = itemView.findViewById(R.id.passport_number)
        private val seatNumber: TextView = itemView.findViewById(R.id.seat_number)
        private val luggage: TextView = itemView.findViewById(R.id.luggage)

        private val returnReservationNumber: TextView = itemView.findViewById(R.id.return_reservation_number)
        private val returnLastName: TextView = itemView.findViewById(R.id.return_last_name)
        private val returnFirstName: TextView = itemView.findViewById(R.id.return_first_name)
        private val returnStartCity: TextView = itemView.findViewById(R.id.return_start_city)
        private val returnArrivalCity: TextView = itemView.findViewById(R.id.return_arrival_city)
        private val returnDepartureDate: TextView = itemView.findViewById(R.id.return_departure_date)
        private val returnDepartureTime: TextView = itemView.findViewById(R.id.return_departure_time)
        private val returnArrivalTime: TextView = itemView.findViewById(R.id.return_arrival_time)
        private val returnPassengers: TextView = itemView.findViewById(R.id.return_passengers)
        private val returnPassportNumber: TextView = itemView.findViewById(R.id.return_passport_number)
        private val returnSeatNumber: TextView = itemView.findViewById(R.id.return_seat_number)
        private val returnLuggage: TextView = itemView.findViewById(R.id.return_luggage)



        fun bind(ticket: FlightReservationCheckDTO) {

            reservationNumber.text = ticket.flightReno.toString()
            lastName.text = ticket.lastName.toString()
            firstName.text = ticket.firstName.toString()
            startCity.text = ticket.startCity.toString()
            arrivalCity.text = ticket.arrivalCity.toString()
            departureDate.text = ticket.departureDate.toString()
            departureTime.text = ticket.departureTime.toString()
            arrivalTime.text = ticket.arrivalTime.toString()
            passengers.text = ticket.numPassengers.toString()
            passportNumber.text = ticket.passport.toString()
            seatNumber.text = ticket.seatNumber.toString()
            luggage.text = ticket.luggage.toString()


            returnReservationNumber.text = ticket.flightReno.toString()
            returnLastName.text = ticket.lastName.toString()
            returnFirstName.text = ticket.firstName.toString()
            returnStartCity.text = ticket.returnStartCity.toString()
            returnArrivalCity.text = ticket.returnArrivalCity.toString()
            returnDepartureDate.text = ticket.returnDepartureDate.toString()
            returnDepartureTime.text = ticket.returnDepartureTime.toString()
            returnArrivalTime.text = ticket.returnArrivalTime.toString()
            returnPassengers.text = ticket.numPassengers.toString()
            returnPassportNumber.text = ticket.passport.toString()
            returnSeatNumber.text = ticket.returnSeatNumber.toString()
            returnLuggage.text = ticket.luggage.toString()


        }
    }


}
