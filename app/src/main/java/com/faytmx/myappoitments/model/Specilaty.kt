package com.faytmx.myappoitments.model

data class Specilaty(val id: Int, val name: String){
    override fun toString(): String {
        return name
    }
}