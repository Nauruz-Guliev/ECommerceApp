package ru.kpfu.itis.gnt.fakestore.model.network.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.kpfu.itis.gnt.fakestore.model.network.LoginResponse
import ru.kpfu.itis.gnt.fakestore.model.network.NetworkUser
import ru.kpfu.itis.gnt.fakestore.model.network.post.LoginPostBody

interface AuthService {
    @POST("auth/login")
    suspend fun login(
        @Body postBody: LoginPostBody
    ): Response<LoginResponse>


    @GET("users/{user-id}")
    suspend fun fetchUser(
        @Path("user-id") userId: Int
    ): Response<NetworkUser>
}