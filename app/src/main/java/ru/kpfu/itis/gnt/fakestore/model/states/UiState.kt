package ru.kpfu.itis.gnt.fakestore.model.states

import ru.kpfu.itis.gnt.fakestore.model.ui.UiProductInCart

sealed interface UiState {
    object Empty: UiState
    data class NonEmpty(val products: List<UiProductInCart>): UiState
}

