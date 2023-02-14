package ru.kpfu.itis.gnt.fakestore.presentation.fragments.main.favorites

import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.airbnb.epoxy.TypedEpoxyController
import fakestore.R
import kotlinx.coroutines.launch
import ru.kpfu.itis.gnt.fakestore.epoxy.presentation.fragments.secondary.ProductInformationFragment
import ru.kpfu.itis.gnt.fakestore.epoxy.presentation.fragments.main.products.ProductsListFragment
import ru.kpfu.itis.gnt.fakestore.presentation.models.ui.UiProduct
import ru.kpfu.itis.gnt.fakestore.presentation.models.states.UiState

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
                ProductInformationFragment.createBundle(uiProduct.product.id)
            )
        } else {
            navController.navigate(
                R.id.action_favoritesListFragment_to_productInformationFragment,
                ProductInformationFragment.createBundle(uiProduct.product.id)
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
