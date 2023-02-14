package ru.kpfu.itis.gnt.fakestore.presentation.models

import ru.kpfu.itis.gnt.fakestore.data.models.Address
import ru.kpfu.itis.gnt.fakestore.data.models.Name

data class User(
    val id: Int,
    val name: Name,
    val userName: String,
    val phoneNumber: String,
    val address: Address,
    val email: String,
)
