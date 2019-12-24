package com.faytmx.myappoitments.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.faytmx.myappoitments.R
import com.faytmx.myappoitments.model.Appointment
import kotlinx.android.synthetic.main.activity_appointments.*

class AppointmentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointments)

        val appointments = ArrayList<Appointment>()

        appointments.add(Appointment(1,"Médico Test","12/12/2019", "3:00 PM"))
        appointments.add(Appointment(2,"Médico AA","15/12/2019", "4:00 PM"))
        appointments.add(Appointment(3,"Médico BB","23/12/2019", "9:00 AM"))

        rVAppointments.layoutManager = LinearLayoutManager(this)
        rVAppointments.adapter =
            AppointmentAdapter(appointments)
    }
}
