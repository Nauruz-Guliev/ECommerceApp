package ru.kpfu.itis.gnt.fakestore.epoxy

import android.view.View
import fakestore.R
import fakestore.databinding.CartEmptyStateModelBinding
import fakestore.databinding.EpoxyModelProductCartItemBinding

data class CartEmptyEpoxyModel(
    private val onClick: (View) -> Unit
) : ViewBindingKotlinModel<CartEmptyStateModelBinding>(R.layout.cart_empty_state_model) {

    override fun CartEmptyStateModelBinding.bind() {
        btnGoShopping.setOnClickListener(onClick)

    }
}
