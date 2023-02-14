package ru.kpfu.itis.gnt.fakestore.data.mappers

import ru.kpfu.itis.gnt.fakestore.presentation.models.User
import ru.kpfu.itis.gnt.fakestore.data.models.Address
import ru.kpfu.itis.gnt.fakestore.data.models.Name
import ru.kpfu.itis.gnt.fakestore.data.models.NetworkUser
import java.util.*
import javax.inject.Inject

class UserMapper @Inject constructor() {
    fun build(networkUser: NetworkUser): User =
        User(
            id = networkUser[0].id,
            name = Name(
                networkUser[0].name.firstname.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                },
                lastname = networkUser[0].name.lastname.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                },
            ),
            userName = networkUser[0].username,
            phoneNumber = networkUser[0].phone,
            address = Address(
                city = networkUser[0].address.city,
                number = networkUser[0].address.number,
                street = networkUser[0].address.street,
                zipcode = networkUser[0].address.zipcode,
                geolocation = networkUser[0].address.geolocation
            ),
            email = networkUser[0].email
        )
}