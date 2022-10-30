package ru.kpfu.itis.gnt.fakestore.fragments.mainFragments

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import fakestore.R
import fakestore.databinding.FragmentProductInformationBinding
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.kpfu.itis.gnt.fakestore.model.ui.UiProduct
import ru.kpfu.itis.gnt.fakestore.viewModels.ProductsListViewModel
import java.text.NumberFormat
import java.util.*


@AndroidEntryPoint
class ProductInformationFragment : Fragment() {


    private var _binding: fakestore.databinding.FragmentProductInformationBinding? = null
    private val binding by lazy { _binding!! }
    private val currencyFormatter = NumberFormat.getCurrencyInstance()
    private var uiProductID: Int = 0

    private val viewModel: ProductsListViewModel by viewModels ()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductInformationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        super.onViewCreated(view, savedInstanceState)
    }


    private fun initViews() {
        uiProductID = arguments?.getInt(ARG_TEXT) ?: 0
        viewModel.uiProductListReducer.reduce(viewModel.store).map { uiProductList ->
            uiProductList.filter {
                it.product.id == uiProductID
            } }.distinctUntilChanged().asLiveData().observe(requireActivity()){
            val uiProduct = it[0]
            currencyFormatter.currency = Currency.getInstance("USD")
            with(binding) {
                tvProductName.text = uiProduct.product.title
                tvProductDescription.text = uiProduct.product.description
                tvProductCategory.text = uiProduct.product.category
                currencyFormatter.currency = Currency.getInstance("USD")
                tvProductCost.text = currencyFormatter.format(uiProduct.product.price)
                ratingBar.rating = uiProduct.product.rating.rate.toFloat()

                btnAddToCart.setOnClickListener {
                    onAddToCartClicked(uiProduct.product.id)
                }
                ivFavorite.setOnClickListener {
                    onFavouriteIconClicked(uiProduct.product.id)
                }
                val imageRes = if (uiProduct.isFavorite) {
                    R.drawable.ic_baseline_favorite_24
                } else {
                    R.drawable.ic_baseline_favorite_border_24
                }
                var inCartText: String
                var inCartDrawable: Int
                if (uiProduct.isInCart) {
                    inCartText = resources.getString(R.string.in_cart)
                    inCartDrawable = R.drawable.in_cart_button_info_fragment_drawable
                } else {
                    inCartText = resources.getString(R.string.add_to_cart)
                    inCartDrawable = R.drawable.add_to_cart_button_info_fragment_drawable

                }
                btnAddToCart.setBackgroundResource(inCartDrawable)
                btnAddToCart.setText(inCartText)
                ivFavorite.setIconResource(imageRes)

                ivProduct.load(
                    uiProduct.product.image
                )
            }
        }
    }

    private fun onFavouriteIconClicked(selectedProductId: Int) {
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState ->
                return@update viewModel.uiProductFavoriteUpdater.update(
                    selectedProductId, currentState
                )
            }
        }
    }


    private fun onAddToCartClicked(selectedProductID: Int) {
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState ->
                return@update viewModel.uiProductInCartUpdater.update(
                    selectedProductID, currentState
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val ARG_TEXT = "ARG_TEXT"
        fun createBundle(uiProductID: Int): Bundle {
            val bundle = Bundle()
            bundle.putInt(ARG_TEXT, uiProductID)
            return bundle
        }
    }

}
