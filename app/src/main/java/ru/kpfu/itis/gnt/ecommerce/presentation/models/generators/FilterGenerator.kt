package ru.kpfu.itis.gnt.ecommerce.presentation.models.generators

import ru.kpfu.itis.gnt.ecommerce.presentation.models.filters.Filter
import ru.kpfu.itis.gnt.ecommerce.domain.models.Product
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
