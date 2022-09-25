package ru.kpfu.itis.gnt.fakestore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import fakestore.databinding.FragmentProductsListBinding
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ru.kpfu.itis.gnt.fakestore.epoxy.UiProductEpoxyController
import ru.kpfu.itis.gnt.fakestore.model.ui.UiFilter
import ru.kpfu.itis.gnt.fakestore.model.ui.UiProduct

@AndroidEntryPoint
class ProductsListFragment : Fragment() {
    private var _binding: FragmentProductsListBinding? = null
    private val binding by lazy { _binding!! }

    private val viewModel: ProductsListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val controller = UiProductEpoxyController(viewModel, navController = navController)


        val spanCount = 2
        val layoutManager = GridLayoutManager(context, spanCount)
        controller.spanCount = spanCount
        layoutManager.spanSizeLookup = controller.spanSizeLookup
        binding.rvRepoxy.layoutManager = layoutManager
        binding.rvRepoxy.setController(controller)
        //controller.setData(emptyList())

        combine(
            viewModel.store.stateFlow.map {
                it.products
            },
            viewModel.store.stateFlow.map {
                it.favouriteProductIDs
            },
            viewModel.store.stateFlow.map {
                it.expandedProductIDs
            },
            viewModel.store.stateFlow.map {
                it.productFilterInfo
            }
        ) { listOfProducts, setOfFavoriteIds, setOfExpandedProductsIDs, productFilterInfo ->
            val uiProducts = listOfProducts.map { product ->
                UiProduct(
                    product = product,
                    isFavorite = setOfFavoriteIds.contains(product.id),
                    isExpanded = setOfExpandedProductsIDs.contains(product.id)
                )
            }

            val uiFilters = productFilterInfo.filters.map { filter ->
                UiFilter(
                    filter = filter,
                    isSelected = productFilterInfo.selectedFilter?.equals(filter) == true
                )
            }.toSet()

            val filterProducts = if (productFilterInfo.selectedFilter == null) {
                uiProducts
            } else {
                uiProducts.filter { it.product.category == productFilterInfo.selectedFilter.value }
            }

            return@combine ProductsListFragmentUiState(uiFilters, filterProducts)


        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner)
        {
            controller.setData(it)
        }

        viewModel.refreshProducts()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
