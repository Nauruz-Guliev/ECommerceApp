package ru.kpfu.itis.gnt.ecommerce.data.repositories

import ru.kpfu.itis.gnt.ecommerce.data.services.ProductsService
import ru.kpfu.itis.gnt.ecommerce.domain.models.Product
import ru.kpfu.itis.gnt.ecommerce.data.mappers.ProductMapper
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productsService: ProductsService,
    private val productMapper: ProductMapper
){
    suspend fun fetchAllProducts() : List<Product> {
        return productsService.getAllProducts().body()?.let {
            it ->
            it.map {
                productMapper.buildFrom(it)
            }
        }?: emptyList()
    }
}
