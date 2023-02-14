package ru.kpfu.itis.gnt.ecommerce.presentation.fragments.main.favorites

import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import coil.load
import fakestore.R
import fakestore.databinding.EpoxyProductItemBinding
import ru.kpfu.itis.gnt.ecommerce.domain.models.ViewBindingKotlinModel
import ru.kpfu.itis.gnt.ecommerce.presentation.models.ui.UiProduct
import java.text.NumberFormat
import java.util.*

class FavoriteFragmentEpoxyModel (
    private val uiProduct: UiProduct,
    val fragment: Fragment,
    val navController: NavController,
    val onFavouriteIconClicked: (Int) -> Unit,
    val onProductClicked: (NavController, UiProduct, Fragment) -> Unit,
    val onAddToCartClicked: (Int) -> Unit
):  ViewBindingKotlinModel<EpoxyProductItemBinding>(R.layout.epoxy_product_item) {
    private val currencyFormatter = NumberFormat.getCurrencyInstance()

    override fun EpoxyProductItemBinding.bind() {
        shimmerIncluder.root.visibility = ViewGroup.INVISIBLE
        tvProductTitle.text = uiProduct.product.title
        tvProductCategory.text = uiProduct.product.category
        currencyFormatter.currency = Currency.getInstance("USD")
        ratingBar.rating = uiProduct.product.rating.rate.toFloat()
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
    }


}
