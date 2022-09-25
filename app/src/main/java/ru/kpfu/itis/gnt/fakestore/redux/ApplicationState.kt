package ru.kpfu.itis.gnt.fakestore.redux

import ru.kpfu.itis.gnt.fakestore.model.domain.Product

data class ApplicationState(
    val products: List<Product> = emptyList()
)
