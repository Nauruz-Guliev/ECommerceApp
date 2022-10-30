package ru.kpfu.itis.gnt.fakestore.model.filters

import ru.kpfu.itis.gnt.fakestore.model.filters.Filter

data class UiFilter(
    val filter: Filter,
    val isSelected: Boolean
)
