package ru.kpfu.itis.gnt.ecommerce.presentation.models.states

import ru.kpfu.itis.gnt.ecommerce.presentation.models.User
import ru.kpfu.itis.gnt.ecommerce.presentation.models.filters.Filter
import ru.kpfu.itis.gnt.ecommerce.domain.models.Product

data class ApplicationState(
    val user: User? = null,
    val products: List<Product> = emptyList(),
    val productFilterInfo: ProductFilterInfo = ProductFilterInfo(),
    val favouriteProductIDs: Set<Int> = emptySet(),
    val inCartProductIDs: Set<Int> = emptySet(),
    //represents a map of productID and its quantity
    val cartQuantityMap: Map <Int, Int> = emptyMap()
) {
    data class ProductFilterInfo(
        val filters: Set<Filter> = emptySet(),
        val selectedFilter: Filter? = null
    )
}
