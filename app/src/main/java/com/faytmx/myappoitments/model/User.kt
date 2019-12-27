package com.faytmx.myappoitments.model

/*
 "id": 1,
        "name": "Emanuel vargas",
        "email": "silverzero55@gmail.com",
        "dni": "12345678",
        "address": "",
        "phone": "",
        "role": "admin"
* */
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val dni: String,
    val address: String,
    val phone: String,
    val role: String
)