package ru.kpfu.itis.gnt.fakestore

import ru.kpfu.itis.gnt.fakestore.model.ui.UiFilter
import ru.kpfu.itis.gnt.fakestore.model.ui.UiProduct

data class ProductsListFragmentUiState(
    val filters: Set<UiFilter>,
    val products: List<UiProduct>
)
