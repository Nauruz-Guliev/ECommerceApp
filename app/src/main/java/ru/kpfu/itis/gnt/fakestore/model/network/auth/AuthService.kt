package ru.kpfu.itis.gnt.fakestore.model.network.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ru.kpfu.itis.gnt.fakestore.model.network.LoginResponse
import ru.kpfu.itis.gnt.fakestore.model.network.NetworkUser
import ru.kpfu.itis.gnt.fakestore.model.network.post.LoginPostBody

interface AuthService {
    @POST("auth/login")
    suspend fun login(
        @Body postBody: LoginPostBody
    ): Response<LoginResponse>
}