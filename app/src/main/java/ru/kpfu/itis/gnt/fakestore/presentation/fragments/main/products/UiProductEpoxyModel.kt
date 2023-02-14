package ru.kpfu.itis.gnt.fakestore.presentation.fragments.main.products

import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import coil.load
import fakestore.R
import fakestore.databinding.EpoxyProductItemBinding
import ru.kpfu.itis.gnt.fakestore.domain.models.ViewBindingKotlinModel

import ru.kpfu.itis.gnt.fakestore.presentation.models.ui.UiProduct
import java.text.NumberFormat
import java.util.*

data class UiProductEpoxyModel(
    val fragment: Fragment,
    val uiProduct: UiProduct?,
    val navController: NavController,
    val onFavouriteIconClicked: (Int) -> Unit,
    val onProductClicked: (NavController, UiProduct, Fragment) -> Unit,
    val onAddToCartClicked: (Int) -> Unit,
    val isSearching: Boolean
) : ViewBindingKotlinModel<EpoxyProductItemBinding>(R.layout.epoxy_product_item) {

    private val currencyFormatter = NumberFormat.getCurrencyInstance()

    override fun EpoxyProductItemBinding.bind() {
        shimmerIncluder.shimmerLayout.isVisible = uiProduct == null
        cardView.isInvisible = uiProduct == null
        if (isSearching) {
            shimmerIncluder.shimmerLayout.isVisible = false
        }
        uiProduct?.let {
            shimmerIncluder.shimmerLayout.stopShimmer()

            tvProductTitle.text = uiProduct.product.title
            tvProductCategory.text = uiProduct.product.category
            // to show products in dollars instead of rubels
            currencyFormatter.currency = Currency.getInstance("USD")
            ratingBar.rating = uiProduct.product.rating.rate.toFloat()
            tvProductPrice.text = currencyFormatter.format(uiProduct.product.price)

            val imageRes = if (uiProduct.isFavorite) {
                R.drawable.ic_baseline_favorite_24
            } else {
                R.drawable.ic_baseline_favorite_border_24
            }

            val inCartDrawable = if (uiProduct.isInCart) {
                 R.drawable.in_cart_button_drawable
            } else {
                 R.drawable.add_to_cart_button_drawable
            }
            btnAddToCart.setBackgroundResource(inCartDrawable)
            ivFavorite.setIconResource(imageRes)

            ivFavorite.setOnClickListener {
                onFavouriteIconClicked(uiProduct.product.id)
            }

            root.setOnClickListener {
                onProductClicked(navController, uiProduct, fragment)
            }
            btnAddToCart.setOnClickListener {
                onAddToCartClicked(uiProduct.product.id)
            }
            contentLoadingProgressBar.isVisible = true
            ivProduct.load(
                data = uiProduct.product.image
            ) {
                listener { request, result ->
                    contentLoadingProgressBar.isGone = true
                }
            }
        } ?: shimmerIncluder.shimmerLayout.startShimmer()
    }
}

