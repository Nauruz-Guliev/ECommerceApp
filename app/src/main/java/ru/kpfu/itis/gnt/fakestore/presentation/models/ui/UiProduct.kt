package ru.kpfu.itis.gnt.fakestore.presentation.models.ui

import ru.kpfu.itis.gnt.fakestore.domain.models.Product
import java.io.Serializable

class UiProduct(
    val product: Product,
    val isFavorite: Boolean = false,
    val isInCart: Boolean = false
) : Serializable
