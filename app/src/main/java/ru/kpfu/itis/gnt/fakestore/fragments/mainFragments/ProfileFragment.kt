package ru.kpfu.itis.gnt.fakestore.fragments.mainFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import dagger.hilt.android.AndroidEntryPoint
import fakestore.R
import fakestore.databinding.FragmentProfileBinding
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ru.kpfu.itis.gnt.fakestore.viewModels.AuthViewModel
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment: Fragment(R.layout.fragment_profile) {
    private var _binding: FragmentProfileBinding? = null
    private val binding by lazy { _binding!! }

    @Inject
    private var profileItemGenerator: UserProfileItemGenerator

    private val authenticationViewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        val uiActions = ProfileUiActions(authenticationViewModel)
        val epoxyController = ProfileEpoxyController(profileItemGenerator, uiActions)
        binding.epoxyProfileItems.setController(epoxyController)
        authenticationViewModel.login(
            userName = "donero",
            password = "ewedon"
        )
        authenticationViewModel.store.stateFlow.map {
            it.user
        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner) {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
