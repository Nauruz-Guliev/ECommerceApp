package ru.kpfu.itis.gnt.ecommerce.presentation.models.states

import ru.kpfu.itis.gnt.ecommerce.presentation.models.ui.UiProductInCart

sealed interface UiState {
    object Empty: UiState
    data class NonEmpty(val products: List<UiProductInCart>): UiState
}

