package ru.kpfu.itis.gnt.ecommerce.presentation.fragments.main.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import fakestore.databinding.FragmentProductsListBinding
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ru.kpfu.itis.gnt.ecommerce.presentation.models.ui.UiProductInCart
import ru.kpfu.itis.gnt.ecommerce.presentation.models.states.UiState

@AndroidEntryPoint
class FavoritesListFragment : Fragment() {
    private var _binding: FragmentProductsListBinding? = null
    private val binding by lazy { _binding!! }
    private val viewModel: FavoritesFragmentViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsListBinding.inflate(layoutInflater)
        return binding.root
    }

    //TODO need to somehow combine with another fragment with similar code

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        val controller = FavoriteFragmentEpoxyController(
            this,
            viewModel,
            navController = navController,
        )

        binding.rvRepoxy.setController(controller)

        viewModel.uiProductListReducer
            .reduce(store = viewModel.store)
            .map {
                it.filter {
                    it.isFavorite
                }.map {
                    UiProductInCart(uiProduct = it, 1)
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
                controller.setData(viewState)
            }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
