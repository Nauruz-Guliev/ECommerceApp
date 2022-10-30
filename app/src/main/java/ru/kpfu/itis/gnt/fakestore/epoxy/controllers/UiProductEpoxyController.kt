package ru.kpfu.itis.gnt.fakestore.epoxy.controllers

import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.TypedEpoxyController
import fakestore.R
import kotlinx.coroutines.launch
import ru.kpfu.itis.gnt.fakestore.model.states.ProductsListFragmentUiState
import ru.kpfu.itis.gnt.fakestore.viewModels.ProductsListViewModel
import ru.kpfu.itis.gnt.fakestore.epoxy.models.UiProductFilterEpoxyModel
import ru.kpfu.itis.gnt.fakestore.epoxy.models.UiProductEpoxyModel
import ru.kpfu.itis.gnt.fakestore.fragments.mainFragments.ProductInformationFragment
import ru.kpfu.itis.gnt.fakestore.fragments.mainFragments.ProductsListFragment
import ru.kpfu.itis.gnt.fakestore.model.filters.Filter
import ru.kpfu.itis.gnt.fakestore.model.ui.UiProduct
import java.util.*

class UiProductEpoxyController(
    private val fragment: Fragment,
    private val viewModel: ProductsListViewModel,
    private val navController: NavController,

    ) : TypedEpoxyController<ProductsListFragmentUiState>() {
    var isSearching = false
    override fun buildModels(data: ProductsListFragmentUiState?) {


        when (data) {
            is ProductsListFragmentUiState.Success -> {
                val uiFilterModels = data.filters.map { uiFilter ->
                    UiProductFilterEpoxyModel(
                        uiFilter = uiFilter,
                        onFilterSelected = ::onFilterSelected,
                        onProductClicked = ::onProductClicked,
                        onAddToCartClicked = ::onAddToCartClicked
                    ).id(uiFilter.filter.value)
                }

                CarouselModel_().models(uiFilterModels).id("filters").addTo(this)

                data.products.forEach {
                    UiProductEpoxyModel(
                        fragment = fragment,
                        it,
                        navController = navController,
                        ::onFavouriteIconClicked,
                        ::onProductClicked,
                        ::onAddToCartClicked,
                        true

                    ).id(it.product.id).addTo(this)
                }
            }
            is ProductsListFragmentUiState.Loading -> {
                repeat(7) {
                    val epoxyId = UUID.randomUUID().toString()
                    UiProductEpoxyModel(
                        fragment = fragment,
                        uiProduct = null,
                        navController = navController,
                        ::onFavouriteIconClicked,
                        ::onProductClicked,
                        ::onAddToCartClicked,
                        isSearching

                    ).id(epoxyId).addTo(this)
                }
            }
            else -> {}
        }
    }
    fun setSearchingOn(isSearching:Boolean) {
        this.isSearching = isSearching
    }


    private fun onFavouriteIconClicked(selectedProductId: Int) {
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState ->
                return@update viewModel.uiProductFavoriteUpdater.update(
                    selectedProductId, currentState
                )
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

    private fun onProductClicked(
        navController: NavController,
        uiProduct: UiProduct,
        fragment: Fragment
    ) {
        if (fragment is ProductsListFragment) {
            navController.navigate(
                R.id.action_productsListFragment_to_productInformationFragment,
                ProductInformationFragment.createBundle(uiProduct.product.id)
            )
        } else {
            navController.navigate(
                R.id.action_favoritesListFragment_to_productInformationFragment,
                ProductInformationFragment.createBundle(uiProduct.product.id)
            )
        }
    }

    private fun onAddToCartClicked(selectedProductID: Int){
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState ->
                return@update viewModel.uiProductInCartUpdater.update(
                    selectedProductID, currentState
                )
            }
        }
    }

}
