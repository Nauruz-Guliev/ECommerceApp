package ru.kpfu.itis.gnt.ecommerce.presentation.redux.updater

import ru.kpfu.itis.gnt.ecommerce.presentation.models.states.ApplicationState
import javax.inject.Inject

class UiProductFavoriteUpdater @Inject constructor() {

    fun update(productID: Int, currentState: ApplicationState): ApplicationState {

        val currentFavoriteIDs = currentState.favouriteProductIDs
        val updatedFavoriteIDs = if (currentFavoriteIDs.contains(productID)) {
            currentFavoriteIDs - productID
        } else {
            currentFavoriteIDs + productID
        }
        return currentState.copy(favouriteProductIDs = updatedFavoriteIDs)
    }
}
