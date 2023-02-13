package ru.kpfu.itis.gnt.fakestore.epoxy.controllers

import com.airbnb.epoxy.Typed2EpoxyController
import com.airbnb.epoxy.TypedEpoxyController
import fakestore.R
import ru.kpfu.itis.gnt.fakestore.epoxy.models.SignOutEpoxyModel
import ru.kpfu.itis.gnt.fakestore.epoxy.models.SignedInItemEpoxyModel
import ru.kpfu.itis.gnt.fakestore.model.User
import ru.kpfu.itis.gnt.fakestore.model.UserProfileItemGenerator

class ProfileEpoxyController(
    private val userProfileGenerator: UserProfileItemGenerator,
    private val profileUiActions: ProfileUi
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

}