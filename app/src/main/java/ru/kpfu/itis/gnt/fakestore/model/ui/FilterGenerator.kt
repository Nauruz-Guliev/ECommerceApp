package ru.kpfu.itis.gnt.fakestore.model.ui

import ru.kpfu.itis.gnt.fakestore.model.domain.Filter
import ru.kpfu.itis.gnt.fakestore.model.domain.Product
import javax.inject.Inject

class FilterGenerator @Inject constructor(){
    fun generate(productsList : List<Product>) : Set<Filter> {
        return productsList.groupBy {
            it.category
        }.map {
            Filter(it.key,"${it.key} (${it.value.size})")
        }.toSet()
    }
}
