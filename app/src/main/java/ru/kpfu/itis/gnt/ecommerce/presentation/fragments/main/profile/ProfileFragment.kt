package ru.kpfu.itis.gnt.ecommerce.presentation.fragments.main.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import dagger.hilt.android.AndroidEntryPoint
import fakestore.R
import fakestore.databinding.FragmentProfileBinding
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ru.kpfu.itis.gnt.ecommerce.presentation.models.generators.UserProfileItemGenerator
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment: Fragment(R.layout.fragment_profile) {
    private var _binding: FragmentProfileBinding? = null
    private val binding by lazy { _binding!! }

    @Inject
    lateinit var profileItemGenerator: UserProfileItemGenerator

    private val authenticationViewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        val uiActions = ProfileActions(authenticationViewModel)
        val epoxyController = ProfileEpoxyController(profileItemGenerator, uiActions)
        binding.epoxyProfileItems.setController(epoxyController)
        authenticationViewModel.login(
            userName = "donero",
            password = "ewedon"
        )
        authenticationViewModel.store.stateFlow.map {
            it.user
        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner) { user ->
            epoxyController.setData(user)
            with(binding) {
                tvHeader.text = user?.userName
                tvEmail.text = user?.email
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
