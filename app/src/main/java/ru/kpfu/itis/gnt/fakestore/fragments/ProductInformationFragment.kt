package ru.kpfu.itis.gnt.fakestore.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import fakestore.R
import fakestore.databinding.FragmentProductInformationBinding
import kotlinx.coroutines.launch
import ru.kpfu.itis.gnt.fakestore.CartFragmentViewModel
import ru.kpfu.itis.gnt.fakestore.ProductsListViewModel
import ru.kpfu.itis.gnt.fakestore.model.ui.UiProduct
import java.text.NumberFormat
import java.util.*

@AndroidEntryPoint
class ProductInformationFragment : Fragment() {


    private var _binding: FragmentProductInformationBinding? = null
    private val binding by lazy { _binding!! }
    private val currencyFormatter = NumberFormat.getCurrencyInstance()
    private lateinit var uiProduct: UiProduct

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
        uiProduct = arguments?.getSerializable(ARG_TEXT) as UiProduct
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

    private fun onFavouriteIconClicked(selectedProductId: Int) {
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState ->
                return@update viewModel.uiProductFavoriteUpdater.update(
                    selectedProductId, currentState
                )
            }
        }
    }
    private fun onProductClicked(
        navController: NavController,
        uiProduct: UiProduct,
        fragment: Fragment
    ) {
        if (fragment is ProductsListFragment) {
            navController.navigate(
                R.id.action_productsListFragment_to_productInformationFragment,
                ProductInformationFragment.createBundle(uiProduct)
            )
        } else {
            navController.navigate(
                R.id.action_favoritesListFragment_to_productInformationFragment,
                ProductInformationFragment.createBundle(uiProduct)
            )
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
