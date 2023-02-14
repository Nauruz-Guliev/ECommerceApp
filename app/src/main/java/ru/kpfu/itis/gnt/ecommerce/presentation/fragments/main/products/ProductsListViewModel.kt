package ru.kpfu.itis.gnt.ecommerce.presentation.fragments.main.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.kpfu.itis.gnt.ecommerce.data.repositories.ProductsRepository
import ru.kpfu.itis.gnt.ecommerce.presentation.models.filters.Filter
import ru.kpfu.itis.gnt.ecommerce.domain.models.Product
import ru.kpfu.itis.gnt.ecommerce.presentation.models.generators.FilterGenerator
import ru.kpfu.itis.gnt.ecommerce.presentation.models.states.ApplicationState
import ru.kpfu.itis.gnt.ecommerce.presentation.redux.Store
import ru.kpfu.itis.gnt.ecommerce.presentation.redux.updater.UiProductInCartUpdater
import ru.kpfu.itis.gnt.ecommerce.presentation.redux.reducer.UiProductListReducer
import ru.kpfu.itis.gnt.ecommerce.presentation.redux.updater.UiProductFavoriteUpdater
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
