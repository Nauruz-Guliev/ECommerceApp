package ru.kpfu.itis.gnt.fakestore.epoxy

import coil.load
import fakestore.R
import fakestore.databinding.EpoxyModelProductCartItemBinding
import ru.kpfu.itis.gnt.fakestore.model.ui.UiProduct
import ru.kpfu.itis.gnt.fakestore.model.ui.UiProductInCart


data class CartItemEpoxyModel(
    val uiProductInCart: UiProductInCart,
    private val onFavoriteClicked: (Int) -> Unit,
    private val onDeleteClicked: (Int) -> Unit,
    private val onProductsAmountChanged: (Int) -> Unit
): ViewBindingKotlinModel<EpoxyModelProductCartItemBinding> (R.layout.epoxy_model_product_cart_item) {
    override fun EpoxyModelProductCartItemBinding.bind() {

        tvProductName.text = uiProductInCart.uiProduct.product.title
        tvProductCategory.text = uiProductInCart.uiProduct.product.category
        tvProductCost.text = uiProductInCart.uiProduct.product.price.toString()
        var isFavoritedText: Int
        var imageRes: Int
        if (uiProductInCart.uiProduct.isFavorite) {
            imageRes = R.drawable.ic_baseline_favorite_24
            isFavoritedText = R.string.is_in_in_favorite
        } else {
            imageRes = R.drawable.ic_baseline_favorite_border_24
            isFavoritedText = R.string.add_to_favorites
        }
        with(selectorIncluder) {
            tvQuantity.apply {
                tvQuantity.text = uiProductInCart.productsAmount.toString()
                btnMinus.setOnClickListener {  onProductsAmountChanged(uiProductInCart.productsAmount-1)}
                btnPlus.setOnClickListener {  onProductsAmountChanged(uiProductInCart.productsAmount+1)}

            }
        }
        ratingBar.rating = uiProductInCart.uiProduct.product.rating.rate.toFloat()

        ivFavorite.setText(isFavoritedText)


        ivFavorite.setOnClickListener { onFavoriteClicked(uiProductInCart.uiProduct.product.id) }
        ivFavorite.setIconResource(imageRes)
        btnDeleteFromCart.setOnClickListener { onDeleteClicked(uiProductInCart.uiProduct.product.id) }

        ivProductCart.load(uiProductInCart.uiProduct.product.image)
    }


}
