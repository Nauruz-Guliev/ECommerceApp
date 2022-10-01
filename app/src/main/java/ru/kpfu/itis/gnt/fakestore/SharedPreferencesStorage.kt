package ru.kpfu.itis.gnt.fakestore

import android.content.Context
import android.content.SharedPreferences
import fakestore.R
import java.util.stream.Collectors

class SharedPreferencesStorage {
    companion object {
        fun saveSetIdsToSharedPreferences(context: Context, sharedPreferences: SharedPreferences, set: Set<Int>) {
            with(sharedPreferences.edit()) {
                this?.putStringSet(context.getString(fakestore.R.string.FAVORITE_IDS), ru.kpfu.itis.gnt.fakestore.SetConverter.intToString(set))
                this?.apply()
            }
        }

        fun getSetIdsFromSharedPreferences(context: Context, sharedPreferences: SharedPreferences): Set<Int> {
            return sharedPreferences.getStringSet(context.getString(R.string.FAVORITE_IDS), emptySet())
                ?.stream()
                ?.map { s -> s.toInt() }
                ?.collect(Collectors.toSet()) as Set<Int>
        }
    }
}
