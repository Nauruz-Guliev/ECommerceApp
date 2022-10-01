package ru.kpfu.itis.gnt.fakestore.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import fakestore.R
import fakestore.databinding.FragmentProductInformationBinding
import ru.kpfu.itis.gnt.fakestore.model.ui.UiProduct
import java.text.NumberFormat
import java.util.*


class ProductInformationFragment : Fragment() {

    private var _binding: FragmentProductInformationBinding? = null
    private val binding by lazy { _binding!! }
    private val currencyFormatter = NumberFormat.getCurrencyInstance()
    private lateinit var uiProduct: UiProduct

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductInformationBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        uiProduct = arguments?.getSerializable(ARG_TEXT) as UiProduct
        currencyFormatter.currency = Currency.getInstance("USD")
        with(binding) {
            tvProductName.text = uiProduct.product.title
            tvProductDescription.text = uiProduct.product.description
            tvProductCategory.text = uiProduct.product.category
            currencyFormatter.currency = Currency.getInstance("USD")
            tvProductCost.text = currencyFormatter.format(uiProduct.product.price)


            val imageRes = if (uiProduct.isFavorite){
                R.drawable.ic_baseline_favorite_24
            } else {
                R.drawable.ic_baseline_favorite_border_24
            }
            ivFavorite.setIconResource(imageRes)


            ivProduct.load(
                uiProduct.product.image
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val ARG_TEXT = "ARG_TEXT"
        fun createBundle(uiProduct: UiProduct): Bundle {
            val bundle = Bundle()
            bundle.putSerializable(ARG_TEXT, uiProduct)
            return bundle
        }
    }

}
