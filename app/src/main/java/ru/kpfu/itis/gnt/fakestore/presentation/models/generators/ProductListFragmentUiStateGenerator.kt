package ru.kpfu.itis.gnt.fakestore.presentation.models.generators

import ru.kpfu.itis.gnt.fakestore.presentation.models.filters.UiFilter
import ru.kpfu.itis.gnt.fakestore.presentation.models.states.ApplicationState
import ru.kpfu.itis.gnt.fakestore.presentation.models.states.ProductsListFragmentUiState
import ru.kpfu.itis.gnt.fakestore.presentation.models.ui.UiProduct
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
