package ru.kpfu.itis.gnt.fakestore.data.models

data class NetworkProduct(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Rating,
    val title: String
)
