package ru.kpfu.itis.gnt.fakestore.presentation

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import fakestore.R
import fakestore.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ru.kpfu.itis.gnt.fakestore.presentation.models.states.ApplicationState
import ru.kpfu.itis.gnt.fakestore.presentation.models.ui.UiProduct
import ru.kpfu.itis.gnt.fakestore.presentation.models.ui.UiProductInCart
import ru.kpfu.itis.gnt.fakestore.presentation.redux.Store
import ru.kpfu.itis.gnt.fakestore.presentation.fragments.main.products.ProductsListViewModel
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.roundToInt

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var store: Store<ApplicationState>

    private val viewModel: ProductsListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.productsListFragment,
                R.id.profileFragment,
                R.id.favoritesListFragment,
                R.id.cartFragment
            )
        )
        val navHostFragment =
            supportFragmentManager.findFragmentById(androidx.navigation.fragment.R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        setupActionBarWithNavController(navController, appBarConfiguration)
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        combine(
            viewModel.uiProductListReducer.reduce(viewModel.store),
            store.stateFlow.map { it.inCartProductIDs.size },
            store.stateFlow.map { it.inCartProductIDs },
            store.stateFlow.map { it.cartQuantityMap }
        ) { uiProductsList, cartSize, inCartProductsIDs, quantityMap ->
            with(binding) {
                bottomNavigationView.getOrCreateBadge(R.id.cartFragment).apply {
                    number = cartSize
                    isVisible = cartSize > 0
                    backgroundColor = resources.getColor(R.color.purple)
                }
                layoutInCart.isVisible = cartSize > 0
                tvTotalCost.text =
                    "Total cost is ${
                        calculateTotalCost(
                            inCartProductsIDs, quantityMap, uiProductsList
                        )
                    }"
            }
            val quantityProductList: MutableList<UiProductInCart> = mutableListOf()
            inCartProductsIDs.forEach {
                val amount = if (quantityMap.containsKey(it)) {
                    quantityMap.getValue(it)
                } else {
                    1
                }
                uiProductsList.forEach { uiProduct ->
                    if(uiProduct.product.id == it) {
                        quantityProductList.add(
                            UiProductInCart(
                                uiProduct = uiProduct,
                                productsAmount = amount
                            )
                        )
                    }

                }
            }
            return@combine quantityProductList
        }/*.map { uiProductList ->
            uiProductList.filter { uiProduct ->
                uiProduct.uiProduct.isInCart
            }.sortedWith { uiProductFirst, uiProductSecond -> uiProductSecond.uiProduct.product.price.toInt() - uiProductFirst.uiProduct.product.price.toInt() }
        }
        */
        .distinctUntilChanged().asLiveData().observe(this) {
            showBottomInCartView(it)
        }

    }


    private fun showBottomInCartView(uiProductList: List<UiProductInCart>) {
        if (uiProductList.isNotEmpty()) {
            with(binding) {
                ivProductFirst.visibility = ViewGroup.VISIBLE
                if (uiProductList.size > 1) {
                    tvQuantityProductsInCart.visibility = ViewGroup.VISIBLE
                    tvQuantityProductsInCart.setText(calculateTotalAmountOfItems(uiProductList).toString())
                } else {
                    tvQuantityProductsInCart.visibility = ViewGroup.GONE
                }
                ivProductFirst.load(uiProductList[0].uiProduct.product.image)
            }
        }
    }

    private fun calculateTotalAmountOfItems(uiProductList: List<UiProductInCart>): Int {
        var amount = 0
        uiProductList.forEach {
            amount += it.productsAmount
        }
        return amount
    }


    fun navigateToShop(@IdRes destinationID: Int) {
        binding.bottomNavigationView.selectedItemId = destinationID
    }

    private fun calculateTotalCost(
        inCartProductIDs: Set<Int>,
        quantityMap: Map<Int, Int>,
        products: List<UiProduct>
    ): Float {
        var totalCost = 0f
        products.forEach { uiProduct ->
            inCartProductIDs.forEach { inCartProductID ->
                if (uiProduct.product.id == inCartProductID) {
                    totalCost = totalCost.plus(uiProduct.product.price.toFloat()).round(2)
                }
                if (quantityMap.size != 0) {
                    quantityMap.forEach { productID, quantity ->
                        if (uiProduct.product.id == productID) {
                            totalCost =
                                totalCost.plus(uiProduct.product.price.toFloat().times(quantity))
                        }
                    }
                }
            }
        }
        return totalCost
    }

    fun Float.round(decimals: Int): Float {
        val multiplier = (10).toFloat().pow(decimals)
        return (this * multiplier).roundToInt().toFloat() / multiplier
    }

}
