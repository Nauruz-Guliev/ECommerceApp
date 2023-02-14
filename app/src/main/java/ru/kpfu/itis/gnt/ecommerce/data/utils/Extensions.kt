package ru.kpfu.itis.gnt.ecommerce.data.utils

import java.util.*


fun String.capitalize() = this.replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(
        Locale.ROOT
    ) else it.toString()
}
