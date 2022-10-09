package ru.kpfu.itis.gnt.fakestore.epoxy

import android.view.View
import fakestore.R
import fakestore.databinding.EpoxyModelProductCartItemBinding

data class CartEmptyEpoxyModel(
    private val onClick: (View) -> Unit
) : ViewBindingKotlinModel<EpoxyModelProductCartItemBinding>(R.layout.epoxy_model_product_cart_item) {

    override fun EpoxyModelProductCartItemBinding.bind() {
        //btnDeleteFromCart.setOnClickListener(onClick)
    }
}
