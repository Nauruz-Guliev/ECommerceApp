package ru.kpfu.itis.gnt.fakestore.epoxy

import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.airbnb.epoxy.TypedEpoxyController
import fakestore.R
import kotlinx.coroutines.launch
import ru.kpfu.itis.gnt.fakestore.FavoritesFragmentViewModel
import ru.kpfu.itis.gnt.fakestore.ProductsListViewModel
import ru.kpfu.itis.gnt.fakestore.fragments.ProductInformationFragment
import ru.kpfu.itis.gnt.fakestore.fragments.ProductsListFragment
import ru.kpfu.itis.gnt.fakestore.model.domain.Filter
import ru.kpfu.itis.gnt.fakestore.model.ui.UiProduct
import ru.kpfu.itis.gnt.fakestore.model.ui.UiState

class FavoriteFragmentEpoxyController(
    private val fragment: Fragment,
    private val viewModel: FavoritesFragmentViewModel,
    private val navController: NavController,
) : TypedEpoxyController<UiState?>() {
    override fun buildModels(data: UiState?) {
        when (data) {
            is UiState.NonEmpty -> {
                data.products
                data.products.forEachIndexed { index, uiProductInCart ->
                    FavoriteFragmentEpoxyModel(
                        uiProduct = uiProductInCart.uiProduct,
                        fragment = fragment,
                        navController = navController,
                        onAddToCartClicked = ::onAddToCartClicked,
                        onFavouriteIconClicked = ::onFavouriteIconClicked,
                        onProductClicked = ::onProductClicked

                    ).id(uiProductInCart.uiProduct.product.id).addTo(this)
                }
            }
            else -> {}
        }
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
    private fun onProductClicked(
        navController: NavController,
        uiProduct: UiProduct,
        fragment: Fragment
    ) {
        if (fragment is ProductsListFragment) {
            navController.navigate(
                R.id.action_productsListFragment_to_productInformationFragment,
                ProductInformationFragment.createBundle(uiProduct)
            )
        } else {
            navController.navigate(
                R.id.action_favoritesListFragment_to_productInformationFragment,
                ProductInformationFragment.createBundle(uiProduct)
            )
        }
    }

    private fun onAddToCartClicked(selectedProductID: Int) {
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState ->
                return@update viewModel.uiProductInCartUpdater.update(
                    selectedProductID, currentState
                )
            }
        }
    }
}
