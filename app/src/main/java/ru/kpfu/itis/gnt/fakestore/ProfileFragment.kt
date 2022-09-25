package ru.kpfu.itis.gnt.fakestore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fakestore.R
import fakestore.databinding.FragmentProfileBinding

class ProfileFragment: Fragment(R.layout.fragment_profile) {
    private var _binding: FragmentProfileBinding? = null
    private val binding by lazy { _binding!! }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
