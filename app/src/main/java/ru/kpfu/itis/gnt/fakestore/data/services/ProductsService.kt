package ru.kpfu.itis.gnt.fakestore.data.services

import retrofit2.Response
import retrofit2.http.GET
import ru.kpfu.itis.gnt.fakestore.data.models.NetworkProduct

interface ProductsService {
    @GET("products")
    suspend fun getAllProducts(): Response<List<NetworkProduct>>
}
