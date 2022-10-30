package ru.kpfu.itis.gnt.fakestore

import android.os.Bundle
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
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
import ru.kpfu.itis.gnt.fakestore.model.domain.Product
import ru.kpfu.itis.gnt.fakestore.model.states.ApplicationState
import ru.kpfu.itis.gnt.fakestore.model.ui.UiProductInCart
import ru.kpfu.itis.gnt.fakestore.redux.Store
import ru.kpfu.itis.gnt.fakestore.viewModels.ProductsListViewModel
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
            store.stateFlow.map { it.products },
            store.stateFlow.map { it.cartQuantityMap }
        ) { uiProducts, productsList, quantityMap ->
            with(binding) {
                bottomNavigationView.getOrCreateBadge(R.id.cartFragment).apply {
                    number = quantityMap.size
                    isVisible = quantityMap.size > 0
                    backgroundColor = resources.getColor(R.color.purple)
                }
                layoutInCart.isVisible = quantityMap.size > 0
                tvTotalCost.text =
                    "Total cost is ${
                        calculateTotalCost(
                            quantityMap, productsList
                        )
                    }"
            }
            val quantityProductList: MutableList<UiProductInCart> = mutableListOf()
            quantityMap.forEach { (productID, amount) ->
                Toast.makeText(baseContext, amount.toString(), Toast.LENGTH_LONG).show()
                quantityProductList.add(
                    UiProductInCart(
                        uiProduct = uiProducts[productID],
                        productsAmount = amount
                    )
                )
            }
            return@combine quantityProductList

        }.map { uiProductList ->
            uiProductList.filter { uiProduct ->
                uiProduct.uiProduct.isInCart
            }.sortedWith { uiProductFirst, uiProductSecond -> uiProductSecond.uiProduct.product.price.toInt() - uiProductFirst.uiProduct.product.price.toInt() }
        }.distinctUntilChanged().asLiveData().observe(this) {
            showBottomInCartView(it)
        }

    }

    private fun showBottomInCartView(uiProductList: List<UiProductInCart>) {
        if (uiProductList.isNotEmpty()) {
            with(binding) {
                val anim = AnimationUtils.loadAnimation(
                    applicationContext,
                    R.anim.elements_animation
                )
                if (ivProductFirst.visibility == ViewGroup.GONE) {
                    ivProductFirst.visibility = ViewGroup.VISIBLE
                    ivProductFirst.startAnimation(anim)
                }
                if (uiProductList.size > 1) {
                    tvQuantityProductsInCart.visibility = ViewGroup.VISIBLE
                    tvQuantityProductsInCart.setText(calculateTotalAmountOfItems(uiProductList))
                } else {
                    tvQuantityProductsInCart.visibility = ViewGroup.GONE
                }
                ivProductFirst.load(uiProductList[0].uiProduct.product.image)
            }
        }
    }

    private fun calculateTotalAmountOfItems(uiProductList: List<UiProductInCart>) : Int{
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
        quantityMap: Map<Int, Int>,
        products: List<Product>
    ): Float {
        var totalCost = 0f
        products.forEach {
                /*
                product ->
            inCartProductIDs.forEach { inCartProductID ->
                if (product.id == inCartProductID) {
                    totalCost = totalCost.plus(product.price.toFloat()).round(2)
                }
                if (quantityMap.isNotEmpty()) {
                    quantityMap.forEach { productID, quantity ->
                        if (product.id == productID) {
                            totalCost = totalCost.plus(product.price.toFloat().times(quantity))
                        }
                    }
                }
                }
                 */

        }
        return totalCost
    }

    private fun Float.round(decimals: Int): Float {
        val multiplier = (10).toFloat().pow(decimals)
        return (this * multiplier).roundToInt().toFloat() / multiplier
    }

}
