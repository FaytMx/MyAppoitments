package com.faytmx.myappoitments.io

import com.faytmx.myappoitments.model.Doctor
import com.faytmx.myappoitments.model.Specilaty
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("specialties")
    abstract fun getSpecialties(): Call<ArrayList<Specilaty>>

    @GET("specialties/{specialty}/doctors")
    abstract fun getDoctor(@Path("specialty") specialtyId: Int):  Call<ArrayList<Doctor>>

    companion object Factory {
        private const val BASE_URL = "http://myappointments.fayt.cc/api/"

        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}