package ru.kpfu.itis.gnt.fakestore.viewModels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.kpfu.itis.gnt.fakestore.model.states.ApplicationState
import ru.kpfu.itis.gnt.fakestore.redux.Store
import ru.kpfu.itis.gnt.fakestore.redux.reducer.UiProductListReducer
import ru.kpfu.itis.gnt.fakestore.redux.updater.UiProductFavoriteUpdater
import ru.kpfu.itis.gnt.fakestore.redux.updater.UiProductInCartUpdater
import javax.inject.Inject

@HiltViewModel
class CartFragmentViewModel @Inject constructor(
    val store: Store<ApplicationState>,
    val uiProductListReducer: UiProductListReducer,
    val uiProductFavoriteUpdater: UiProductFavoriteUpdater,
    val uiProductInCartUpdater: UiProductInCartUpdater
): ViewModel() {
}
