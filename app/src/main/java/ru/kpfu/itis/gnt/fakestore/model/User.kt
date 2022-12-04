package ru.kpfu.itis.gnt.fakestore.model

import ru.kpfu.itis.gnt.fakestore.model.network.Address
import ru.kpfu.itis.gnt.fakestore.model.network.Name

data class User(
    val id: Int,
    val name: Name,
    val userName: String,
    val phoneNumber: String,
    val address: Address
)
