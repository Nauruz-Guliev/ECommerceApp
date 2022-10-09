package ru.kpfu.itis.gnt.fakestore.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import dagger.hilt.android.AndroidEntryPoint
import fakestore.databinding.FragmentProductsCartBinding
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ru.kpfu.itis.gnt.fakestore.CartFragmentViewModel
import ru.kpfu.itis.gnt.fakestore.epoxy.CartFragmentEpoxyController
import ru.kpfu.itis.gnt.fakestore.model.ui.UiState

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

        val epoxyController = CartFragmentEpoxyController()
        binding.rvRepoxy.setController(epoxyController)

        viewModel.uiProductListReducer
            .reduce(store = viewModel.store)
            .map {
                it.filter {
                    it.isInCart
                }
            }
            .distinctUntilChanged()
            .asLiveData()
            .observe(viewLifecycleOwner){
                val viewState = if(it.isEmpty()) {
                    UiState.Empty
                } else {
                    UiState.NonEmpty(it)
                }
                epoxyController.setData(viewState)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}

