package ru.kpfu.itis.gnt.fakestore.model.mapper

import ru.kpfu.itis.gnt.fakestore.model.domain.Product
import ru.kpfu.itis.gnt.fakestore.model.network.NetworkProduct
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import javax.inject.Inject

class ProductMapper
@Inject constructor()
{
    fun buildFrom(networkProduct: NetworkProduct) : Product {
        return Product(
            category = capitalize(networkProduct.category),
            description = networkProduct.description,
            id = networkProduct.id,
            image = networkProduct.image,
            price = BigDecimal(networkProduct.price).setScale(2, RoundingMode.HALF_UP),
            title = networkProduct.title,
            rating = networkProduct.rating
        )
    }
    private fun capitalize(text: String) :String{
        return text.replaceFirstChar { if(it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()}
    }
}
