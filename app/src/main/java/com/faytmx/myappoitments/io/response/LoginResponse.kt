package com.faytmx.myappoitments.io.response

import com.faytmx.myappoitments.model.User

data class LoginResponse(val success: Boolean, val user: User, val jwt: String)