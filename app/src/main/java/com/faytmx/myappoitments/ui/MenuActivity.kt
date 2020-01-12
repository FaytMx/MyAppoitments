package com.faytmx.myappoitments.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.faytmx.myappoitments.util.PreferenceHelper
import com.faytmx.myappoitments.util.PreferenceHelper.set
import com.faytmx.myappoitments.util.PreferenceHelper.get
import com.faytmx.myappoitments.R
import com.faytmx.myappoitments.io.ApiService
import com.faytmx.myappoitments.model.User
import com.faytmx.myappoitments.util.toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_profile.*
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

    private val authHeader by lazy {
        val jwt = preferences["jwt", ""]
        "Bearer $jwt"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val storeToken = intent.getBooleanExtra("store_token", false)
        if (storeToken)
            storeToken()

        setOnClickListeners()

    }

    private fun setOnClickListeners() {
        btnProfile.setOnClickListener {
            editProfile()
        }

        btnCreateAppointmen.setOnClickListener {
            createAppointment(it)

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

    private fun createAppointment(view: View) {
        val call = apiService.getUser(authHeader)

        call.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                toast(t.localizedMessage)
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    val phoneLength = user?.phone?.length ?: 0

                    if (phoneLength >= 6) {
                        val intent = Intent(
                            this@MenuActivity,
                            CreateAppointmentActivity::class.java
                        )
                        startActivity(intent)
                    } else {
                        Snackbar.make(view, R.string.you_need_a_phone, Snackbar.LENGTH_LONG).show()
                    }
                }
            }

        })


    }

    private fun editProfile() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun storeToken() {

        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(this) { instanceIdResult ->
            val deviceToken = instanceIdResult.token
            val call = apiService.postToken(authHeader, deviceToken)
            call.enqueue(object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    toast(t.localizedMessage)
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Log.d(Companion.TAG, "Token registrado correctamente")
                    } else {
                        Log.d(Companion.TAG, "Hubo un problema al registrar el token")
                    }
                }

            })
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
        val call = apiService.postLogout(authHeader)

        call.enqueue(object : Callback<Void> {
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

    companion object {
        private const val TAG = "MenuActivity"
    }

}
