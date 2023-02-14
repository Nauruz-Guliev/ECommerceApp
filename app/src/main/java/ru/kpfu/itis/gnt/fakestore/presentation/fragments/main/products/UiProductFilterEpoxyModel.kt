package ru.kpfu.itis.gnt.fakestore.presentation.fragments.main.products

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import fakestore.R
import fakestore.databinding.EpoxyModelProductFilterBinding
import ru.kpfu.itis.gnt.fakestore.domain.models.ViewBindingKotlinModel
import ru.kpfu.itis.gnt.fakestore.presentation.models.filters.Filter
import ru.kpfu.itis.gnt.fakestore.presentation.models.filters.UiFilter
import ru.kpfu.itis.gnt.fakestore.presentation.models.ui.UiProduct

data class UiProductFilterEpoxyModel(
    val uiFilter: UiFilter,
    val onFilterSelected: (Filter) -> Unit,
    val onProductClicked: (NavController, UiProduct, Fragment) -> Unit,
    val onAddToCartClicked: (Int) -> Unit
 ) : ViewBindingKotlinModel<EpoxyModelProductFilterBinding>(R.layout.epoxy_model_product_filter) {

    override fun EpoxyModelProductFilterBinding.bind() {
        root.setOnClickListener { onFilterSelected(uiFilter.filter) }
        filterNameTextView.text = uiFilter.filter.displayText

        val cardBackgroundColorResId = if (uiFilter.isSelected) {
            R.color.green_100
        } else {
            R.color.green_500
        }
        root.setCardBackgroundColor(ContextCompat.getColor(root.context, cardBackgroundColorResId))
    }
}
