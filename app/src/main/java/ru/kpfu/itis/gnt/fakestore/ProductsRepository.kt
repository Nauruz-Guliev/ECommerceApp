package ru.kpfu.itis.gnt.fakestore

import ru.kpfu.itis.gnt.fakestore.hilt.service.ProductsService
import ru.kpfu.itis.gnt.fakestore.model.domain.Product
import ru.kpfu.itis.gnt.fakestore.model.mapper.ProductMapper
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
