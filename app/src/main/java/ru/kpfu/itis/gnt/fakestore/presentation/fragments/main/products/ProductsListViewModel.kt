package ru.kpfu.itis.gnt.fakestore.presentation.fragments.main.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.kpfu.itis.gnt.fakestore.data.repositories.ProductsRepository
import ru.kpfu.itis.gnt.fakestore.presentation.models.filters.Filter
import ru.kpfu.itis.gnt.fakestore.domain.models.Product
import ru.kpfu.itis.gnt.fakestore.presentation.models.generators.FilterGenerator
import ru.kpfu.itis.gnt.fakestore.presentation.models.states.ApplicationState
import ru.kpfu.itis.gnt.fakestore.presentation.redux.Store
import ru.kpfu.itis.gnt.fakestore.presentation.redux.updater.UiProductInCartUpdater
import ru.kpfu.itis.gnt.fakestore.presentation.redux.reducer.UiProductListReducer
import ru.kpfu.itis.gnt.fakestore.presentation.redux.updater.UiProductFavoriteUpdater
import javax.inject.Inject

@HiltViewModel
class ProductsListViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
    val store: Store<ApplicationState>,
    val uiProductFavoriteUpdater: UiProductFavoriteUpdater,
    private val filter: FilterGenerator,
    val uiProductInCartUpdater: UiProductInCartUpdater,
    val uiProductListReducer: UiProductListReducer
) : ViewModel() {

    fun refreshProducts() =
        viewModelScope.launch {
            if(store.read { it.products }.isNotEmpty()) return@launch
            val products: List<Product> = productsRepository.fetchAllProducts()
            val filters: Set<Filter> = filter.generate(products)
            store.update {
                return@update it.copy(
                    products = products,
                    productFilterInfo = ApplicationState.ProductFilterInfo(
                        filters = filters,
                        selectedFilter = it.productFilterInfo.selectedFilter
                    )
                )
            }
        }
}
