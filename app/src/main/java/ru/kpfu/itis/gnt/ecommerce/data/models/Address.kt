package ru.kpfu.itis.gnt.ecommerce.data.models

data class Address(
    val city: String,
    val geolocation: Geolocation,
    val number: Int,
    val street: String,
    val zipcode: String
)