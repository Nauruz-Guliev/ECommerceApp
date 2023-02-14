package ru.kpfu.itis.gnt.ecommerce.domain.models

import ru.kpfu.itis.gnt.ecommerce.data.models.Rating
import java.io.Serializable
import java.math.BigDecimal

class Product(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: BigDecimal,
    val title: String,
    val rating: Rating
) : Serializable
