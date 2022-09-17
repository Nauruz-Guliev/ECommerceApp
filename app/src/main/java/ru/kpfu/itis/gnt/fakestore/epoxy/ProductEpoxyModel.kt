package ru.kpfu.itis.gnt.fakestore.epoxy

import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import coil.load
import fakestore.R
import fakestore.databinding.EpoxyProductItemBinding

import ru.kpfu.itis.gnt.fakestore.model.domain.Product
import java.text.NumberFormat
import java.util.*

data class ProductEpoxyModel(
    val product: Product?
) : ViewBindingKotlinModel<EpoxyProductItemBinding>(R.layout.epoxy_product_item) {

    private val currencyFormatter = NumberFormat.getCurrencyInstance()

    override fun EpoxyProductItemBinding.bind() {
        shimmerLayout.isVisible = product == null
        cardView.isInvisible = product == null

        product?.let {
            shimmerLayout.stopShimmer()

            tvProductTitle.text = product.title
            tvProductDescription.text = product.description
            tvProductCategory.text = product.category

            // to show products in dollars instead of rubels
            currencyFormatter.currency = Currency.getInstance("USD")

            tvProductPrice.text = currencyFormatter.format(product.price)
            tvProductDescription.isVisible = false

            contentLoadingProgressBar.isVisible = true
            ivProduct.load(
                data = product.image
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
            cardView.setOnClickListener {
                tvProductDescription.apply {
                    isVisible = !isVisible
                }
            }

            btnAddToCart.setOnClickListener {
                btnInCart.apply {
                    isVisible = !isVisible
                }
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

