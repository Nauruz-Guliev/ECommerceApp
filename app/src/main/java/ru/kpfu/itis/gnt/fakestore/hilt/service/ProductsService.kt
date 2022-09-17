package ru.kpfu.itis.gnt.fakestore.hilt.service

import retrofit2.Response
import retrofit2.http.GET
import ru.kpfu.itis.gnt.fakestore.model.network.NetworkProduct

interface ProductsService {
    @GET("products")
    suspend fun getAllProducts(): Response<List<NetworkProduct>>
}
