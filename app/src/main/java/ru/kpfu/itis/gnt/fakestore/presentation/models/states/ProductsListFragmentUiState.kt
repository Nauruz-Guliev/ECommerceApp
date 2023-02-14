package ru.kpfu.itis.gnt.fakestore.presentation.models.states

import ru.kpfu.itis.gnt.fakestore.presentation.models.filters.UiFilter
import ru.kpfu.itis.gnt.fakestore.presentation.models.ui.UiProduct

sealed interface  ProductsListFragmentUiState {
    data class Success(val filters: Set<UiFilter>,
                       val products: List<UiProduct>) : ProductsListFragmentUiState

    object Loading: ProductsListFragmentUiState
}
