package ru.kpfu.itis.gnt.ecommerce.presentation.fragments.main.cart

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.kpfu.itis.gnt.ecommerce.presentation.models.states.ApplicationState
import ru.kpfu.itis.gnt.ecommerce.presentation.redux.Store
import ru.kpfu.itis.gnt.ecommerce.presentation.redux.reducer.UiProductListReducer
import ru.kpfu.itis.gnt.ecommerce.presentation.redux.updater.UiProductFavoriteUpdater
import ru.kpfu.itis.gnt.ecommerce.presentation.redux.updater.UiProductInCartUpdater
import javax.inject.Inject

@HiltViewModel
class CartFragmentViewModel @Inject constructor(
    val store: Store<ApplicationState>,
    val uiProductListReducer: UiProductListReducer,
    val uiProductFavoriteUpdater: UiProductFavoriteUpdater,
    val uiProductInCartUpdater: UiProductInCartUpdater
): ViewModel() {
}
