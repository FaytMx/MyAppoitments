package com.faytmx.myappoitments.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.faytmx.myappoitments.util.PreferenceHelper
import com.faytmx.myappoitments.util.PreferenceHelper.set
import com.faytmx.myappoitments.util.PreferenceHelper.get
import com.faytmx.myappoitments.R
import com.faytmx.myappoitments.io.ApiService
import com.faytmx.myappoitments.util.toast
import kotlinx.android.synthetic.main.activity_menu.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuActivity : AppCompatActivity() {
    private val apiService by lazy {
        ApiService.create()
    }

    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btnCreateAppointmen.setOnClickListener {
            val intent = Intent(
                this,
                CreateAppointmentActivity::class.java
            )
            startActivity(intent)
        }

        btnMyAppointment.setOnClickListener {
            val intent = Intent(
                this,
                AppointmentsActivity::class.java
            )
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            performLogout()

        }
    }

    private fun clearSessionPreference() {
//        val preferences = getSharedPreferences("general", Context.MODE_PRIVATE)
//        val editor = preferences.edit()
//        editor.putBoolean("session", false)
//        editor.apply()


        preferences["jwt"] = ""
    }

    private fun performLogout() {
        val jwt = preferences["jwt", ""]
        val call = apiService.postLogout("Bearer $jwt")

        call.enqueue(object: Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                toast(t.localizedMessage)
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                clearSessionPreference()
                val intent = Intent(this@MenuActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        })
    }

}
