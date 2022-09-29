package ru.kpfu.itis.gnt.fakestore.model.ui

import ru.kpfu.itis.gnt.fakestore.model.domain.Filter

data class UiFilter(
    val filter: Filter,
    val isSelected: Boolean
)
