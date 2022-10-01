package ru.kpfu.itis.gnt.fakestore.model.ui

import android.os.Parcelable
import ru.kpfu.itis.gnt.fakestore.model.domain.Product
import java.io.Serializable

class UiProduct(
    val product: Product,
    val isFavorite: Boolean = false,
    val isInCart: Boolean = false
) : Serializable
