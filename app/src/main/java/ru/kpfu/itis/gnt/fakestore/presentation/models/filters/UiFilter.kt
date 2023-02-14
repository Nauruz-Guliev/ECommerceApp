package ru.kpfu.itis.gnt.fakestore.presentation.models.filters

import ru.kpfu.itis.gnt.fakestore.presentation.models.filters.Filter

data class UiFilter(
    val filter: Filter,
    val isSelected: Boolean
)
