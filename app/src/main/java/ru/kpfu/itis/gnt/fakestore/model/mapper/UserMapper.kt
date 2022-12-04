package ru.kpfu.itis.gnt.fakestore.model.mapper

import ru.kpfu.itis.gnt.fakestore.model.User
import ru.kpfu.itis.gnt.fakestore.model.network.Address
import ru.kpfu.itis.gnt.fakestore.model.network.Name
import ru.kpfu.itis.gnt.fakestore.model.network.NetworkUser
import java.util.*
import javax.inject.Inject

class UserMapper @Inject constructor() {
    fun build(networkUser: NetworkUser): User =
        User(
            id = networkUser[0].id,
            name = Name(
                networkUser[0].name.firstname.capitalize(),
                lastname = networkUser[0].name.lastname.capitalize(),
            ),
            userName = networkUser[0].username,
            phoneNumber = networkUser[0].phone,
            address = Address(
                city = networkUser[0].address.city,
                number = networkUser[0].address.number,
                street = networkUser[0].address.street,
                zipcode = networkUser[0].address.zipcode,
                geolocation = networkUser[0].address.geolocation
            )
        )
}

fun String.capitalize() = this.replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(
        Locale.ROOT
    ) else it.toString()
}
