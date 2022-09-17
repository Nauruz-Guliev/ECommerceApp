package ru.kpfu.itis.gnt.fakestore.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import ru.kpfu.itis.gnt.fakestore.model.domain.Product

class ProductEpoxyController : TypedEpoxyController<List<Product>>() {

    override fun buildModels(data: List<Product>?) {
        if (data.isNullOrEmpty()) {
            repeat(7) {
                val epoxyId = it + 1
                ProductEpoxyModel(product = null).id(epoxyId).addTo(this)
            }
            return
        }

        data.forEach {
            ProductEpoxyModel(it).id(it.id).addTo(this)
        }
    }
}
