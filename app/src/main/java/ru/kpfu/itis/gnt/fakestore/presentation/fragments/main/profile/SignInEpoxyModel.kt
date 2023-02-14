package ru.kpfu.itis.gnt.fakestore.presentation.fragments.main.profile

import androidx.annotation.DrawableRes
import fakestore.R
import fakestore.databinding.ProfileSignedInBinding
import ru.kpfu.itis.gnt.fakestore.domain.models.ViewBindingKotlinModel

data class SignInEpoxyModel(
    @DrawableRes val iconRes: Int,
    val headerText: String,
    val infoText: String,
    val onClick: () -> Unit
) : ViewBindingKotlinModel<ProfileSignedInBinding>(R.layout.profile_signed_in) {

    override fun ProfileSignedInBinding.bind() {
        iconImageView.setImageResource(iconRes)
        headerTextView.text = headerText
        infoTextView.text = infoText
        root.setOnClickListener { onClick() }
    }
}