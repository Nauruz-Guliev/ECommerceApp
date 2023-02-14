package ru.kpfu.itis.gnt.ecommerce.presentation.models.generators

import androidx.annotation.DrawableRes
import fakestore.R
import ru.kpfu.itis.gnt.ecommerce.presentation.models.User
import javax.inject.Inject

class UserProfileItemGenerator @Inject constructor() {
    data class UserProfileItem(
        @DrawableRes val iconRes: Int,
        val headerText: String,
        val infoText: String
    )

    fun buildItems(user: User) =
        buildList {
            add(
                UserProfileItem(
                    iconRes = R.drawable.ic_outline_person_24,
                    headerText = "Username",
                    infoText = user.userName
                )
            )
            add(
                UserProfileItem(
                iconRes = R.drawable.ic_baseline_local_phone_24,
                headerText = "Phonenumber",
                infoText = user.phoneNumber
            )
            )
            add(
                UserProfileItem(
                iconRes = R.drawable.ic_baseline_location_on_24,
                headerText = "Location",
                infoText = user.address.city
            )
            )
        }
}