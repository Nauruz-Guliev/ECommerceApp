package ru.kpfu.itis.gnt.fakestore.epoxy.controllers

import androidx.lifecycle.viewModelScope
import com.airbnb.epoxy.TypedEpoxyController
import kotlinx.coroutines.launch
import ru.kpfu.itis.gnt.fakestore.viewModels.CartFragmentViewModel
import ru.kpfu.itis.gnt.fakestore.epoxy.models.CartEmptyEpoxyModel
import ru.kpfu.itis.gnt.fakestore.epoxy.models.CartItemEpoxyModel
import ru.kpfu.itis.gnt.fakestore.model.states.UiState

class CartFragmentEpoxyController(
    private val viewModel: CartFragmentViewModel,
    private val onEmptyStateClicked:()-> Unit
)
: TypedEpoxyController<UiState?>() {

    override fun buildModels(data: UiState?) {
        when (data) {
            null, is UiState.Empty -> {
                CartEmptyEpoxyModel(onClick = {
                    onEmptyStateClicked()
                }) .id("cart_empty").addTo(this)
            }
            is UiState.NonEmpty -> {
                data.products.forEachIndexed { index, uiProductInCart ->
                    CartItemEpoxyModel(
                        uiProductInCart = uiProductInCart,
                        onFavoriteClicked = ::onFavouriteIconClicked,
                        onDeleteClicked = ::onAddToCartClicked,
                        onProductsAmountChanged = {
                            newAmount ->
                            if(newAmount < 1) {
                                onAddToCartClicked(uiProductInCart.uiProduct.product.id)
                                return@CartItemEpoxyModel
                            }
                            viewModel.viewModelScope.launch {
                                viewModel.store.update { currentState ->
                                    val newMapEntry = uiProductInCart.uiProduct.product.id to newAmount
                                    val newMap = currentState.cartQuantityMap + newMapEntry
                                    return@update currentState.copy(cartQuantityMap = newMap)
                                }
                            }
                        }
                    ).id(uiProductInCart.uiProduct.product.id).addTo(this)
                }
            }

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


