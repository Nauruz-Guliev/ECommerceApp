package ru.kpfu.itis.gnt.fakestore

import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import fakestore.R
import fakestore.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ru.kpfu.itis.gnt.fakestore.model.domain.Product
import ru.kpfu.itis.gnt.fakestore.redux.ApplicationState
import ru.kpfu.itis.gnt.fakestore.redux.Store
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.roundToInt

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var store: Store<ApplicationState>

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
            store.stateFlow.map { it.inCartProductIDs.size },
            store.stateFlow.map { it.inCartProductIDs },
            store.stateFlow.map { it.products },
            store.stateFlow.map { it.cartQuantityMap }
        ) { cartSize, inCartProductsIDs, productsList, quantityMap ->
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
                            inCartProductsIDs,
                            quantityMap,
                            productsList
                        )
                    }"
            }

        }.distinctUntilChanged().asLiveData().observe(this) {

        }

    }

    fun navigateToShop(@IdRes destinationID: Int) {
        binding.bottomNavigationView.selectedItemId = destinationID
    }

    private fun calculateTotalCost(
        inCartProductIDs: Set<Int>,
        quantityMap: Map<Int, Int>,
        products: List<Product>
    ): Float {
        var totalCost = 0f
        products.forEach { product ->
            inCartProductIDs.forEach { inCartProductID ->
                if (product.id == inCartProductID) {
                    totalCost = totalCost.plus(product.price.toFloat()).round(2)
                }
                if (quantityMap.size != 0) {
                    quantityMap.forEach { productID, quantity ->
                        if(product.id == productID) {
                            totalCost = totalCost.plus(product.price.toFloat().times(quantity))
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
