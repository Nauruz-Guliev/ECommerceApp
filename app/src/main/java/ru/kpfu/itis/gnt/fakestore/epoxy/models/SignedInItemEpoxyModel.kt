package ru.kpfu.itis.gnt.fakestore.epoxy.models

class SignedInItemEpoxyModel(
    iconRes: Int,
    headerText: String,
    infoText: String,
    onClick: () -> Unit
) : ViewBindingKotlinModel<EpoxyModelPro>
