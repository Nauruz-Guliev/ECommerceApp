package ru.kpfu.itis.gnt.fakestore.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import ru.kpfu.itis.gnt.fakestore.model.ui.UiState

class CartFragmentEpoxyController : TypedEpoxyController<UiState?>() {

    override fun buildModels(data: UiState?) {
        when (data) {
            null, is UiState.Empty -> {
                CartEmptyEpoxyModel {

                }.id("empty").addTo(this)
            }
            is UiState.NonEmpty -> {
                data.products.forEachIndexed { index, uiProduct ->
                    CartItemEpoxyModel(
                        uiProduct = uiProduct,
                        onFavoriteClicked = {

                        },
                        onDeleteClicked = {

                        }
                    ).id(uiProduct.product.id).addTo(this)
                }
            }
        }
    }
}


