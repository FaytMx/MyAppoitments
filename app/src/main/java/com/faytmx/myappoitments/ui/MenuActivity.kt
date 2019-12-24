package com.faytmx.myappoitments.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.faytmx.myappoitments.PreferenceHelper
import com.faytmx.myappoitments.PreferenceHelper.set
import com.faytmx.myappoitments.R
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btnCreateAppointmen.setOnClickListener{
            val intent = Intent(this,
                CreateAppointmentActivity::class.java)
            startActivity(intent)
        }

        btnMyAppointment.setOnClickListener{
            val intent = Intent(this,
                AppointmentsActivity::class.java)
            startActivity(intent)
        }

        btnLogout.setOnClickListener{
            clearSessionPreference()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun clearSessionPreference() {
//        val preferences = getSharedPreferences("general", Context.MODE_PRIVATE)
//        val editor = preferences.edit()
//        editor.putBoolean("session", false)
//        editor.apply()

        val preferences =
            PreferenceHelper.defaultPrefs(this)
        preferences["session"] = false
    }

}