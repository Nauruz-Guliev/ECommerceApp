package ru.kpfu.itis.gnt.ecommerce.presentation.fragments.main.cart

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import coil.load
import fakestore.R
import fakestore.databinding.EpoxyModelProductCartItemBinding
import ru.kpfu.itis.gnt.ecommerce.domain.models.ViewBindingKotlinModel
import ru.kpfu.itis.gnt.ecommerce.presentation.models.ui.UiProductInCart


data class CartItemEpoxyModel(
    val uiProductInCart: UiProductInCart,
    private val onFavoriteClicked: (Int) -> Unit,
    private val onDeleteClicked: (Int) -> Unit,
    private val onProductsAmountChanged: (Int) -> Unit
): ViewBindingKotlinModel<EpoxyModelProductCartItemBinding>(R.layout.epoxy_model_product_cart_item) {
    override fun EpoxyModelProductCartItemBinding.bind() {

        tvSwipeToDelete.translationX = 0f

        tvProductName.text = uiProductInCart.uiProduct.product.title
        tvProductCategory.text = uiProductInCart.uiProduct.product.category
        tvProductCost.text = uiProductInCart.uiProduct.product.price.toString()
        val isFavoritedText: Int
        val imageRes: Int
        if (uiProductInCart.uiProduct.isFavorite) {
            imageRes = R.drawable.ic_baseline_favorite_24
            isFavoritedText = R.string.is_in_in_favorite
        } else {
            imageRes = R.drawable.ic_baseline_favorite_border_24
            isFavoritedText = R.string.add_to_favorites
        }

        with(selectorIncluder) {
            tvQuantity.apply {
                val vibration = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                tvQuantity.text = uiProductInCart.productsAmount.toString()
                btnMinus.setOnClickListener {
                    onProductsAmountChanged(uiProductInCart.productsAmount-1)
                    vibration.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE))
                }
                btnPlus.setOnClickListener {
                    vibration.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE))
                    onProductsAmountChanged(uiProductInCart.productsAmount+1)
                }

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
