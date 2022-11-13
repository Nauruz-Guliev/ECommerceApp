package ru.kpfu.itis.gnt.fakestore.model.network

data class Address(
    val city: String,
    val geolocation: Geolocation,
    val number: Int,
    val street: String,
    val zipcode: String
)