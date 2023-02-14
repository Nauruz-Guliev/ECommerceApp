package ru.kpfu.itis.gnt.fakestore.presentation.redux.reducer

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ru.kpfu.itis.gnt.fakestore.presentation.models.ui.UiProduct
import ru.kpfu.itis.gnt.fakestore.presentation.models.states.ApplicationState
import ru.kpfu.itis.gnt.fakestore.presentation.redux.Store
import javax.inject.Inject

class UiProductListReducer @Inject constructor() {
    fun reduce(store: Store<ApplicationState>): Flow<List<UiProduct>> {
        return combine(
            store.stateFlow.map {
                it.products
            },
            store.stateFlow.map {
                it.favouriteProductIDs
            },
            store.stateFlow.map {
                it.productFilterInfo
            },
            store.stateFlow.map {
                it.inCartProductIDs
            }
        ) { listOfProducts, setOfFavoriteIds, productFilterInfo, inCartProductIDs ->

            if (listOfProducts.isEmpty()) {
                return@combine emptyList<UiProduct>()
            }
            return@combine listOfProducts.map { product ->
                UiProduct(
                    product = product,
                    isFavorite = setOfFavoriteIds.contains(product.id),
                    isInCart = inCartProductIDs.contains(product.id)
                )
            }

        }.distinctUntilChanged()
    }
}
