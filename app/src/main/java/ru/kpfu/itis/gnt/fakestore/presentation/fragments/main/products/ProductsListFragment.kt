package ru.kpfu.itis.gnt.fakestore.presentation.fragments.main.products

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import fakestore.R
import fakestore.databinding.FragmentProductsListBinding
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ru.kpfu.itis.gnt.fakestore.presentation.MainActivity
import ru.kpfu.itis.gnt.fakestore.presentation.models.generators.ProductListFragmentUiStateGenerator
import ru.kpfu.itis.gnt.fakestore.presentation.models.ui.UiProduct
import javax.inject.Inject

@AndroidEntryPoint
class ProductsListFragment : Fragment() {
    private var _binding: FragmentProductsListBinding? = null
    private val binding by lazy { _binding!! }

    private val viewModel: ProductsListViewModel by viewModels()

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var searchView: SearchView

    @Inject
    lateinit var uiStateGenerator: ProductListFragmentUiStateGenerator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bottomNavigationView = (activity as MainActivity).findViewById(R.id.bottomNavigationView)
        searchView = (activity as MainActivity).findViewById(R.id.search_view)

        _binding = FragmentProductsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        val controller = UiProductEpoxyController(this, viewModel, navController = navController)


        binding.rvRepoxy.setController(controller)

        combine(
            viewModel.uiProductListReducer.reduce(viewModel.store),
            viewModel.store.stateFlow.map { it.productFilterInfo }
        ) { uiProducts, productFilterInfo ->
            uiStateGenerator.generate(uiProducts, productFilterInfo)
        }.distinctUntilChanged().asLiveData().observe(requireActivity())
        {
            controller.setSearchingOn(false)
            controller.setData(it)
        }
        viewModel.refreshProducts()
        setUpSearch(controller)
    }

    private fun setUpSearch(controller: UiProductEpoxyController) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                bottomNavigationView.selectedItemId = R.id.productsListFragment
                return true;
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                bottomNavigationView.selectedItemId = R.id.productsListFragment
                combine(
                    viewModel.uiProductListReducer.reduce(viewModel.store).map { uiProducts ->
                        val filteredUiProducts: MutableList<UiProduct> = mutableListOf()
                        uiProducts.forEach { product ->
                            if (product.product.title.toLowerCase().contains(newText.toString().toLowerCase())) {
                                filteredUiProducts.add(
                                    product
                                )
                            }
                        }
                        with (binding) {
                            if (filteredUiProducts.size == 0) {
                                tvSearchError.apply {
                                    text = "${newText} was not found."
                                    visibility = ViewGroup.VISIBLE
                                    rvRepoxy.visibility = ViewGroup.INVISIBLE
                                }
                            } else {
                                rvRepoxy.visibility = ViewGroup.VISIBLE
                                tvSearchError.visibility = ViewGroup.INVISIBLE
                            }
                        }
                        Log.d("text", newText.toString())
                        if (newText != null) {
                            return@map filteredUiProducts
                        } else {
                            return@map uiProducts
                        }
                    },
                    viewModel.store.stateFlow.map { it.productFilterInfo }
                ) { uiProducts, productFilterInfo ->
                    uiStateGenerator.generate(uiProducts, productFilterInfo)
                }
                    .distinctUntilChanged().asLiveData().observe(activity!!) {
                        controller.setSearchingOn(true)
                        controller.setData(it)
                    }
                bottomNavigationView.selectedItemId = R.id.productsListFragment
                return true
            }
        }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
