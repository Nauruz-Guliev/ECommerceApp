package ru.kpfu.itis.gnt.fakestore.epoxy

import android.content.Context
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.NavController
import coil.load
import fakestore.R
import fakestore.databinding.EpoxyProductItemBinding

import ru.kpfu.itis.gnt.fakestore.model.ui.UiProduct
import java.text.NumberFormat
import java.util.*

data class UiProductEpoxyModel(
    val uiProduct: UiProduct?,
    val navController: NavController,
    val onFavouriteIconClicked: (Int) -> Unit,
    val onProductClicked: (NavController, UiProduct) -> Unit
) : ViewBindingKotlinModel<EpoxyProductItemBinding>(R.layout.epoxy_product_item) {

    private val currencyFormatter = NumberFormat.getCurrencyInstance()

    override fun EpoxyProductItemBinding.bind() {
        shimmerLayout.isVisible = uiProduct == null
        cardView.isInvisible = uiProduct == null

        uiProduct?.let {
            shimmerLayout.stopShimmer()

            tvProductTitle.text = uiProduct.product.title
            tvProductCategory.text = uiProduct.product.category
            // to show products in dollars instead of rubels
            currencyFormatter.currency = Currency.getInstance("USD")

            tvProductPrice.text = currencyFormatter.format(uiProduct.product.price)

            val imageRes = if (uiProduct.isFavorite){
                R.drawable.ic_baseline_favorite_24
            } else {
                R.drawable.ic_baseline_favorite_border_24
            }
            ivFavorite.setIconResource(imageRes)

            ivFavorite.setOnClickListener {
                onFavouriteIconClicked(uiProduct.product.id)
            }

            root.setOnClickListener {
                onProductClicked(navController, uiProduct)
            }

            contentLoadingProgressBar.isVisible = true
            ivProduct.load(
                data = uiProduct.product.image
            ) {
                listener { request, result ->
                    contentLoadingProgressBar.isGone = true
                }
            }
        } ?: shimmerLayout.startShimmer()

        setOnClickListeners(this)
    }

    fun setOnClickListeners(binding: EpoxyProductItemBinding) {
        with(binding) {

            btnAddToCart.setOnClickListener {

            }
            var isFavorite = false
            ivFavorite.setOnClickListener {
                val imageRes = if (isFavorite) {
                    R.drawable.ic_baseline_favorite_border_24
                } else {
                    R.drawable.ic_baseline_favorite_24
                }
                ivFavorite.setIconResource(imageRes)
                isFavorite = !isFavorite
            }
        }
    }
}

