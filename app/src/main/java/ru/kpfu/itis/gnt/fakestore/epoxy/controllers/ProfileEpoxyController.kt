package ru.kpfu.itis.gnt.fakestore.epoxy.controllers

import androidx.annotation.DrawableRes
import com.airbnb.epoxy.TypedEpoxyController
import fakestore.R
import fakestore.databinding.ProfileSignedInBinding
import ru.kpfu.itis.gnt.fakestore.epoxy.models.SignOutEpoxyModel
import ru.kpfu.itis.gnt.fakestore.epoxy.models.ViewBindingKotlinModel
import ru.kpfu.itis.gnt.fakestore.fragments.mainFragments.ProfileActions
import ru.kpfu.itis.gnt.fakestore.model.User
import ru.kpfu.itis.gnt.fakestore.model.UserProfileItemGenerator

class ProfileEpoxyController(
    private val userProfileGenerator: UserProfileItemGenerator,
    private val profileUiActions: ProfileActions
): TypedEpoxyController<User?>(){
    override fun buildModels(data: User?) {
        if(data == null) {
            SignOutEpoxyModel(
                onSignIn = { userName, password ->
                    profileUiActions.onSignIn(userName, password)
                },
                errorMessage = "couldn't login"
            ).id("signedout").addTo(this)
        } else {
            userProfileGenerator.buildItems(data).forEach {
                profileItem ->
                SignedInItemEpoxyModel(
                    iconRes = profileItem.iconRes,
                    headerText = profileItem.headerText,
                    infoText = profileItem.infoText,
                    onClick = { profileUiActions.onProfileItemSelected(profileItem.iconRes) }
                )
            }

            SignedInItemEpoxyModel(
                iconRes = R.drawable.ic_baseline_logout_24,
                headerText = "Logout",
                infoText = "LEAAAVE",
                onClick = { profileUiActions.onProfileItemSelected(R.drawable.ic_baseline_logout_24) }
            )

        }
    }


    data class SignedInItemEpoxyModel(
        @DrawableRes val iconRes: Int,
        val headerText: String,
        val infoText: String,
        val onClick: () -> Unit
    ) : ViewBindingKotlinModel<ProfileSignedInBinding>(R.layout.profile_signed_in) {

        override fun ProfileSignedInBinding.bind() {
           // iconImageView.setImageResource(iconRes)
           // headerTextView.text = headerText
           // infoTextView.text = infoText
            root.setOnClickListener { onClick() }
        }
    }

}