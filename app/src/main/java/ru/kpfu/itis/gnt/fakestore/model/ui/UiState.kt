package ru.kpfu.itis.gnt.fakestore.model.ui

sealed interface UiState {
    object Empty: UiState
    data class NonEmpty(val products: List<UiProduct>):UiState
}

