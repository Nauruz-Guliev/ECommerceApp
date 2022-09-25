package ru.kpfu.itis.gnt.fakestore.redux

import ru.kpfu.itis.gnt.fakestore.model.domain.Filter
import ru.kpfu.itis.gnt.fakestore.model.domain.Product

data class ApplicationState(
    val products: List<Product> = emptyList(),
    val productFilterInfo: ProductFilterInfo = ProductFilterInfo(),
    val favouriteProductIDs: Set<Int> = emptySet(),
    val expandedProductIDs: Set<Int> = emptySet()

) {
    data class ProductFilterInfo(
        val filters: Set<Filter> = emptySet(),
        val selectedFilter: Filter? = null
    )
}
