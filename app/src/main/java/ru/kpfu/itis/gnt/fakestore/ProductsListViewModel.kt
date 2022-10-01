package ru.kpfu.itis.gnt.fakestore

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.kpfu.itis.gnt.fakestore.model.domain.Product
import ru.kpfu.itis.gnt.fakestore.model.ui.FilterGenerator
import ru.kpfu.itis.gnt.fakestore.model.ui.UiProduct
import ru.kpfu.itis.gnt.fakestore.redux.ApplicationState
import ru.kpfu.itis.gnt.fakestore.redux.Store
import javax.inject.Inject

@HiltViewModel
class ProductsListViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
    val store: Store<ApplicationState>,
    private val filter: FilterGenerator
) : ViewModel() {

    fun refreshProducts(isFavoritesFragment: Boolean, context: Context, sharedPreferences: SharedPreferences) =
        viewModelScope.launch {
            var products: List<Product> = productsRepository.fetchAllProducts()
            val filters = if (isFavoritesFragment) {
                val filteredIds = SharedPreferencesStorage.getSetIdsFromSharedPreferences(context, sharedPreferences)
                products = products.filter {
                    filteredIds.contains(it.id)
                }
                filter.generate(products)
            } else {
                filter.generate(products)
            }
            store.update { it ->
                return@update it.copy(
                    products = products,
                    productFilterInfo = ApplicationState.ProductFilterInfo(
                        filters = filters,
                        selectedFilter = null
                    )
                )
            }
        }
}
