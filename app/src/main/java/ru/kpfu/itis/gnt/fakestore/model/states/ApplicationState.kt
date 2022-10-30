package ru.kpfu.itis.gnt.fakestore.model.states

import ru.kpfu.itis.gnt.fakestore.model.filters.Filter
import ru.kpfu.itis.gnt.fakestore.model.domain.Product

data class ApplicationState(
    val products: List<Product> = emptyList(),
    val productFilterInfo: ProductFilterInfo = ProductFilterInfo(),
    val favouriteProductIDs: Set<Int> = emptySet(),
    //represents a map of productID and its quantity
    val cartQuantityMap: MutableMap <Int, Int> = mutableMapOf()
) {
    data class ProductFilterInfo(
        val filters: Set<Filter> = emptySet(),
        val selectedFilter: Filter? = null
    )
}
