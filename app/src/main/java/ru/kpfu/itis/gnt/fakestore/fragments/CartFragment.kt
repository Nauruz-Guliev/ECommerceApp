package ru.kpfu.itis.gnt.fakestore.fragments

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.airbnb.epoxy.EpoxyTouchHelper
import dagger.hilt.android.AndroidEntryPoint
import fakestore.R
import fakestore.databinding.FragmentProductsCartBinding
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.kpfu.itis.gnt.fakestore.CartFragmentViewModel
import ru.kpfu.itis.gnt.fakestore.MainActivity
import ru.kpfu.itis.gnt.fakestore.epoxy.CartFragmentEpoxyController
import ru.kpfu.itis.gnt.fakestore.epoxy.CartItemEpoxyModel
import ru.kpfu.itis.gnt.fakestore.model.ui.UiProductInCart
import ru.kpfu.itis.gnt.fakestore.model.ui.UiState
import java.lang.Float.max

@AndroidEntryPoint
class CartFragment: Fragment() {
    private var _binding : FragmentProductsCartBinding? = null
    private val binding by lazy {_binding!!}

    private val viewModel: CartFragmentViewModel by viewModels ()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsCartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val epoxyController = CartFragmentEpoxyController(viewModel) {
            (activity as? MainActivity)?.navigateToShop(R.id.productsListFragment)
        }
        binding.rvRepoxy.setController(epoxyController)

        val uiProductsInCartFlow = viewModel.uiProductListReducer
            .reduce(store = viewModel.store)
            .map {
                it.filter {
                    it.isInCart
                }
            }
        combine(uiProductsInCartFlow, viewModel.store.stateFlow.map {
            it.cartQuantityMap
        }) {
            uiProducts, quantityMap ->
            uiProducts.map {
                UiProductInCart(it, quantityMap[it.product.id]?:1)
            }
        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner) {

            val viewState = if(it.isEmpty()) {
                UiState.Empty
            } else {
                UiState.NonEmpty(it)
            }
            epoxyController.setData(viewState)
        }

        EpoxyTouchHelper
            .initSwiping(binding.rvRepoxy)
            .right()
            .withTarget(CartItemEpoxyModel::class.java)
            .andCallbacks(object:EpoxyTouchHelper.SwipeCallbacks<CartItemEpoxyModel>(){
                override fun onSwipeCompleted(
                    model: CartItemEpoxyModel?,
                    itemView: View?,
                    position: Int,
                    direction: Int
                ) {
                    model?.let {
                        epoxyModel->
                        viewModel.viewModelScope.launch {
                            viewModel.store.update { state ->
                                return@update viewModel.uiProductInCartUpdater.update(
                                    productID = epoxyModel.uiProductInCart.uiProduct.product.id,
                                    currentState = state
                                )
                            }
                        }
                    }
                }

                override fun onSwipeProgressChanged(
                    model: CartItemEpoxyModel?,
                    itemView: View?,
                    swipeProgress: Float,
                    canvas: Canvas?
                ) {
                    itemView?.findViewById<View>(R.id.tv_swipe_to_delete)?.apply {
                        val imageView = itemView.findViewById<ImageView>(R.id.iv_product_cart)
                        translationX = max(-itemView.translationX, - imageView.width.toFloat())
                    }
                }

            })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}

