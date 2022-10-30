package ru.kpfu.itis.gnt.fakestore.redux.updater

import ru.kpfu.itis.gnt.fakestore.model.states.ApplicationState
import javax.inject.Inject

class UiProductInCartUpdater @Inject constructor() {
    fun update(productID: Int, currentState: ApplicationState) : ApplicationState {
        val productsInCart = currentState.inCartProductIDs
        val updatedProductsInCartIDs = if(productsInCart.contains(productID)) {
            productsInCart - productID
        } else {
            productsInCart + productID
        }
        return currentState.copy(inCartProductIDs = updatedProductsInCartIDs)
    }
}
