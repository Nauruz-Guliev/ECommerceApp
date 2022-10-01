package ru.kpfu.itis.gnt.fakestore.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fakestore.databinding.FragmentProductsCartBinding

class CartFragment: Fragment() {
    private var _binding : FragmentProductsCartBinding? = null
    private val binding by lazy {_binding!!}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsCartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
