package ru.kpfu.itis.gnt.fakestore.fragments.mainFragments

import fakestore.R
import ru.kpfu.itis.gnt.fakestore.viewModels.AuthViewModel


class ProfileActions(private val viewModel: AuthViewModel) {

    fun onSignIn(username: String, password: String) {
        viewModel.login(username, password)
    }

    fun onProfileItemSelected(id: Int) {
        when (id) {
            R.drawable.baseline_smartphone_24 -> {}
            R.drawable.ic_baseline_location_on_24 -> {}
            R.drawable.baseline_logout_24 -> {}
            else -> {
                //todo
            }
        }
    }
}