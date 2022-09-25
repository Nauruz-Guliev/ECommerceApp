package ru.kpfu.itis.gnt.fakestore.epoxy

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.TypedEpoxyController
import fakestore.R
import kotlinx.coroutines.launch
import ru.kpfu.itis.gnt.fakestore.ProductInformationFragment
import ru.kpfu.itis.gnt.fakestore.ProductsListFragmentUiState
import ru.kpfu.itis.gnt.fakestore.ProductsListViewModel
import ru.kpfu.itis.gnt.fakestore.UiProductFilterEpoxyModel
import ru.kpfu.itis.gnt.fakestore.model.domain.Filter
import ru.kpfu.itis.gnt.fakestore.model.ui.UiProduct

class UiProductEpoxyController(
    private val viewModel: ProductsListViewModel,
    private val navController: NavController
) : TypedEpoxyController<ProductsListFragmentUiState>() {
    override fun buildModels(data: ProductsListFragmentUiState?) {
        if (data == null) {
            repeat(7) {
                val epoxyId = it + 1
                UiProductEpoxyModel(
                    uiProduct = null,
                    navController = navController,
                    ::onFavouriteIconClicked,
                    ::onProductClicked
                ).id(epoxyId).addTo(this)
            }
            return
        }
        val uiFilterModels = data.filters.map { uiFilter ->
            UiProductFilterEpoxyModel(
                uiFilter = uiFilter,
                onFilterSelected = ::onFilterSelected,
                onProductClicked = ::onProductClicked
            ).id(uiFilter.filter.value)
        }

        CarouselModel_().models(uiFilterModels).id("filters").addTo(this)

        data.products.forEach {
            UiProductEpoxyModel(
                it, navController = navController, ::onFavouriteIconClicked, ::onProductClicked
            ).id(it.product.id).addTo(this)
        }
    }

    private fun onFavouriteIconClicked(selectedProductId: Int) {
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState ->

                val currentFavouriteIDs = currentState.favouriteProductIDs
                val newFavoriteIDs = if (currentFavouriteIDs.contains(selectedProductId)) {
                    currentFavouriteIDs.filter {
                        it != selectedProductId
                    }.toSet()
                } else {
                    currentFavouriteIDs + setOf(selectedProductId)
                }
                return@update currentState.copy(favouriteProductIDs = newFavoriteIDs)
            }
        }
    }

    private fun onFilterSelected(filter: Filter) {
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState ->
                val currentlySelectedFilter = currentState.productFilterInfo.selectedFilter
                return@update currentState.copy(
                    productFilterInfo = currentState.productFilterInfo.copy(
                        selectedFilter = if (currentlySelectedFilter != filter) {
                            filter
                        } else {
                            null
                        }
                    )
                )
            }
        }
    }

    private fun onProductClicked(navController: NavController, uiProduct: UiProduct) {

        navController.navigate(
            R.id.action_productsListFragment_to_productInformationFragment,
            ProductInformationFragment.createBundle(uiProduct)
        )
    }

}
