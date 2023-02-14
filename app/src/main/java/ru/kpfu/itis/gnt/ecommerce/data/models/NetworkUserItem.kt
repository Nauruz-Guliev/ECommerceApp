package ru.kpfu.itis.gnt.ecommerce.data.models

data class NetworkUserItem(
    val __v: Int,
    val address: Address,
    val email: String,
    val id: Int,
    val name: Name,
    val password: String,
    val phone: String,
    val username: String
)