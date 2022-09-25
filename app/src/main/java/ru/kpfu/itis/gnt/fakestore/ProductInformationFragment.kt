package ru.kpfu.itis.gnt.fakestore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import coil.load
import fakestore.databinding.FragmentProductInformationBinding
import ru.kpfu.itis.gnt.fakestore.model.ui.UiProduct


class ProductInformationFragment : Fragment() {

    private var _binding: FragmentProductInformationBinding? = null
    private val binding by lazy { _binding!! }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductInformationBinding.inflate(inflater, container, false)
        val product = arguments?.getSerializable(ARG_TEXT) as UiProduct
        Toast.makeText(context, product.product.toString(), Toast.LENGTH_SHORT).show()
        with(binding) {
            tvProductName.text = product.product.title
            tvProductCost.text = product.product.price.toString()
            tvProductDescription.text = product.product.description
            ivProduct.load(
                product.product.image
            )
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val ARG_TEXT = "ARG_TEXT"
        fun createBundle(uiProduct: UiProduct) :Bundle {
            val bundle = Bundle()
            bundle.putSerializable(ARG_TEXT, uiProduct)
            return bundle
        }
    }

}
