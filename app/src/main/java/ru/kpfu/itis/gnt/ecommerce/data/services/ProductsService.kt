package ru.kpfu.itis.gnt.ecommerce.data.services

import retrofit2.Response
import retrofit2.http.GET
import ru.kpfu.itis.gnt.ecommerce.data.models.NetworkProduct

interface ProductsService {
    @GET("products")
    suspend fun getAllProducts(): Response<List<NetworkProduct>>
}
