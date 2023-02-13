package ru.kpfu.itis.gnt.fakestore.epoxy.controllers

import androidx.annotation.DrawableRes
import com.airbnb.epoxy.Typed2EpoxyController
import com.airbnb.epoxy.TypedEpoxyController
import fakestore.R
import fakestore.databinding.ProfileSignedInBinding
import fakestore.databinding.ProfileSignedOutBinding
import ru.kpfu.itis.gnt.fakestore.epoxy.models.SignOutEpoxyModel
import ru.kpfu.itis.gnt.fakestore.epoxy.models.SignedInItemEpoxyModel
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
                {
                    userName, password ->
                    profileUiActions.onSignIn(userName, password)
                }
            ).id("signedout").addTo(this)
        } else {
            userProfileGenerator.buildItems(data).forEach {
                profileItem ->
                SignedInItemEpoxyModel(
                    iconRes = profileItem.iconRes,
                    headerText = profileItem.headerText,
                    infoText = profileItem.infoText,
                    onClick = {profileUiActions.onProfileItemClicked(profileItem.iconRes)}
                )
            }

            SignedInItemEpoxyModel(
                iconRes = R.drawable.ic_baseline_logout_24,
                headerText = "Logout",
                infoText = "LEAAAVE",
                onClick = {profileUiActions.onProfileItemClicked(R.drawable.ic_baseline_logout_24)}
            )

        }
    }
    data class SignedOutEpoxyModel(
        val onSignIn: (String, String) -> Unit,
        val errorMessage: String?
    ) : ViewBindingKotlinModel<ProfileSignedOutBinding>(R.layout.profile_signed_out) {

        override fun ProfileSignedOutBinding.bind() {
            passwordLayout.error = errorMessage
            signInButton.setOnClickListener {
                val username = usernameEditText.text?.toString()
                val password = passwordEditText.text?.toString()

                if (username.isNullOrBlank() || password.isNullOrBlank()) {
                    passwordLayout.error = "Both fields required"
                    return@setOnClickListener
                }

                passwordLayout.error = null
                onSignIn(username, password)
            }
        }

        override fun ProfileSignedOutBinding.unbind() {
            /*
            usernameEditText.text = null
            usernameEditText.clearFocus()
            passwordEditText.text = null
            passwordEditText.clearFocus()
            passwordLayout.error = null

             */
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