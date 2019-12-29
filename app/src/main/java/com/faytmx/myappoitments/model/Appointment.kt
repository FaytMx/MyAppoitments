package com.faytmx.myappoitments.model

import com.google.gson.annotations.SerializedName


/*
* {
        "id": 1,
        "description": "Servicio de Dentista",
        "specialty_id": 3,
        "scheduled_date": "2019-11-14",
        "type": "Examen",
        "created_at": "2019-11-13 03:34:17",
        "status": "Cancelada",
        "scheduled_time_12": "9:00 AM",
        "specialty": {
            "id": 3,
            "name": "Neurología"
        },
        "doctor": {
            "id": 2,
            "name": "Doctor Test"
        }
* */
data class Appointment(
    val id: Int,
    val description: String,
    val type: String,
    val status: String,
    @SerializedName("scheduled_date") val scheduledDate: String,
    @SerializedName("scheduled_time_12") val scheduledTime: String,
    @SerializedName("created_at") val createdAt: String,

    val specialty: Specialty,
    val doctor: Doctor
    )