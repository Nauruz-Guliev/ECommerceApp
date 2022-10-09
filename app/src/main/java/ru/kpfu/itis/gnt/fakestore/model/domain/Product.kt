package ru.kpfu.itis.gnt.fakestore.model.domain

import java.io.Serializable
import java.math.BigDecimal

class Product(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: BigDecimal,
    val title: String,
    val rating: ru.kpfu.itis.gnt.fakestore.model.network.Rating
) : Serializable
