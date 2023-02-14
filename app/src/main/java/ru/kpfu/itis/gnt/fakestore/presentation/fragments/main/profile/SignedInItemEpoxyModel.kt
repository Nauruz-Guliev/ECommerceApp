package ru.kpfu.itis.gnt.fakestore.presentation.fragments.main.profile

import ru.kpfu.itis.gnt.fakestore.domain.models.ViewBindingKotlinModel

class SignedInItemEpoxyModel(
    iconRes: Int,
    headerText: String,
    infoText: String,
    onClick: () -> Unit
) : ViewBindingKotlinModel<EpoxyModelPro>
