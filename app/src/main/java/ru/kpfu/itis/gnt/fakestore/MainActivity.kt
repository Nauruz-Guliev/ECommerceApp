package ru.kpfu.itis.gnt.fakestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import fakestore.databinding.ActivityMainBinding
import ru.kpfu.itis.gnt.fakestore.epoxy.ProductEpoxyController
import ru.kpfu.itis.gnt.fakestore.hilt.service.ProductsService
import ru.kpfu.itis.gnt.fakestore.model.domain.Product
import ru.kpfu.itis.gnt.fakestore.model.mapper.ProductMapper
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var productsService: ProductsService

    @Inject
    lateinit var productMapper: ProductMapper

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val controller = ProductEpoxyController()
        binding.rvRepoxy.setController(controller)
        controller.setData(emptyList())

        lifecycleScope.launchWhenStarted {
            val response = productsService.getAllProducts()
            val domainProducts: List<Product> = response.body()!!.map {
                productMapper.buildFrom(networkProduct = it)
            }
            controller.setData(domainProducts)
        }
    }

}
