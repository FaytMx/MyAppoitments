package com.faytmx.myappoitments.io

import com.faytmx.myappoitments.model.Doctor
import com.faytmx.myappoitments.model.Schedule
import com.faytmx.myappoitments.model.Specilaty
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("specialties")
    fun getSpecialties(): Call<ArrayList<Specilaty>>

    @GET("specialties/{specialty}/doctors")
    fun getDoctor(@Path("specialty") specialtyId: Int): Call<ArrayList<Doctor>>

    @GET("schedule/hours")
    fun getHours(@Query("doctor_id") doctorId: Int, @Query("date") date: String): Call<Schedule>

    companion object Factory {
        private const val BASE_URL = "http://myappointments.fayt.cc/api/"

        fun create(): ApiService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}