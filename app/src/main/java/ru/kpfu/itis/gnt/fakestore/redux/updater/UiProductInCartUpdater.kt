package ru.kpfu.itis.gnt.fakestore.redux.updater

import ru.kpfu.itis.gnt.fakestore.model.states.ApplicationState
import javax.inject.Inject

class UiProductInCartUpdater @Inject constructor() {
    fun update(productID: Int, currentState: ApplicationState) : ApplicationState {
     //   val productsInCart = currentState.inCartProductIDs
        val productsInCart = currentState.cartQuantityMap
        if(productsInCart.containsKey(productID)) {
            productsInCart.remove(productID)
        } else {
            productsInCart[productID] = 1
        }
        return currentState.copy(cartQuantityMap = productsInCart)
    }
}
