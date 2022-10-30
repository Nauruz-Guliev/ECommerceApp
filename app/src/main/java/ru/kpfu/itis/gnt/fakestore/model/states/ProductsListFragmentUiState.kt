package ru.kpfu.itis.gnt.fakestore.model.states

import ru.kpfu.itis.gnt.fakestore.model.filters.UiFilter
import ru.kpfu.itis.gnt.fakestore.model.ui.UiProduct

sealed interface  ProductsListFragmentUiState {
    data class Success(val filters: Set<UiFilter>,
                       val products: List<UiProduct>) : ProductsListFragmentUiState

    object Loading: ProductsListFragmentUiState
}
