package ru.kpfu.itis.gnt.fakestore

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import fakestore.R
import fakestore.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ru.kpfu.itis.gnt.fakestore.epoxy.UiProductEpoxyController
import ru.kpfu.itis.gnt.fakestore.model.ui.UiProduct
import ru.kpfu.itis.gnt.fakestore.redux.ApplicationState
import ru.kpfu.itis.gnt.fakestore.redux.Store
import javax.inject.Inject

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
                R.id.productInformationFragment,
                R.id.favoritesListFragment,
                R.id.cartFragment
            )
        )
        val navHostFragment = supportFragmentManager.findFragmentById(androidx.navigation.fragment.R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        setupActionBarWithNavController(navController, appBarConfiguration)
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        store.stateFlow.map {
            it.inCartProductIDs.size
        }.distinctUntilChanged().asLiveData().observe(this) {
            binding.bottomNavigationView.getOrCreateBadge(R.id.cartFragment).apply {
                number = it
                isVisible = it>0
            }
        }

    }

}
