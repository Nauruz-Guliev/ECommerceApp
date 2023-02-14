package ru.kpfu.itis.gnt.ecommerce.presentation.fragments.main.cart

import android.view.View
import fakestore.R
import fakestore.databinding.CartEmptyStateModelBinding
import ru.kpfu.itis.gnt.ecommerce.domain.models.ViewBindingKotlinModel

data class CartEmptyEpoxyModel(
    private val onClick: (View) -> Unit
) : ViewBindingKotlinModel<CartEmptyStateModelBinding>(R.layout.cart_empty_state_model) {

    override fun CartEmptyStateModelBinding.bind() {
        btnGoShopping.setOnClickListener(onClick)

    }
}
