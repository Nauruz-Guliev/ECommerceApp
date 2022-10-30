package ru.kpfu.itis.gnt.fakestore.model.generators

import ru.kpfu.itis.gnt.fakestore.model.filters.UiFilter
import ru.kpfu.itis.gnt.fakestore.model.states.ApplicationState
import ru.kpfu.itis.gnt.fakestore.model.states.ProductsListFragmentUiState
import ru.kpfu.itis.gnt.fakestore.model.ui.UiProduct
import javax.inject.Inject

class ProductListFragmentUiStateGenerator @Inject constructor() {
    fun generate(
        uiProducts: List<UiProduct>,
        filterInfo: ApplicationState.ProductFilterInfo
    ): ProductsListFragmentUiState {

        if (uiProducts.isEmpty()) {
            return ProductsListFragmentUiState.Loading
        }

        val uiFilters = filterInfo.filters.map {
            UiFilter(
                it, filterInfo.selectedFilter?.equals(it) == true
            )
        }.toSet()

        val filteredProducts = if(filterInfo.selectedFilter == null) {
            uiProducts
        } else {
            uiProducts.filter {
                it.product.category == filterInfo.selectedFilter.value
            }
        }
        return ProductsListFragmentUiState.Success(uiFilters, filteredProducts)
    }
}
