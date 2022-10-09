package ru.kpfu.itis.gnt.fakestore.epoxy

import coil.load
import fakestore.R
import fakestore.databinding.EpoxyModelProductCartItemBinding
import ru.kpfu.itis.gnt.fakestore.model.ui.UiProduct

data class CartItemEpoxyModel (
    private val uiProduct: UiProduct,
    private val onFavoriteClicked: () -> Unit,
    private val onDeleteClicked: () -> Unit,
): ViewBindingKotlinModel<EpoxyModelProductCartItemBinding> (R.layout.epoxy_model_product_cart_item) {
    override fun EpoxyModelProductCartItemBinding.bind() {

        tvProductName.text = uiProduct.product.title
        tvProductCategory.text = uiProduct.product.category
        tvProductCost.text = uiProduct.product.price.toString()
        val imageRes = if(uiProduct.isFavorite) {
            R.drawable.ic_baseline_favorite_24
        } else {
            R.drawable.ic_baseline_favorite_border_24
        }
        ivFavorite.setIconResource(imageRes)
        btnDeleteFromCart.setOnClickListener { onDeleteClicked() }

        ivProduct.load(uiProduct.product.image)
    }


}
