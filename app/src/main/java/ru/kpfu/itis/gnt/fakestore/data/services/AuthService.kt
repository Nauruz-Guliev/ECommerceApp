package ru.kpfu.itis.gnt.fakestore.data.services

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.kpfu.itis.gnt.fakestore.data.models.LoginResponse
import ru.kpfu.itis.gnt.fakestore.data.models.NetworkUser
import ru.kpfu.itis.gnt.fakestore.data.models.post.LoginPostBody

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